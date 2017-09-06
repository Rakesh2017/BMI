 package com.enhabyto.bmiapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


 public class LoginSignupStartPage extends AppCompatActivity {

     protected EditText email, password;
     protected ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
     protected Button button_login;

     private FirebaseAuth mAuth;
     private String TAG = "tag message ";
     private String emailText, passwordText;
     private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
     private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

     SharedPreferences sharedPreferences;
     SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();

        }
        catch (NullPointerException e){
            Toast.makeText(this, "Null Pointer Exception", Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_login_signup_start_page);
        Window window = getWindow();
            //change notification color
            // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        //now change color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(LoginSignupStartPage.this,R.color.red));
        }

        ScaleAnimation animation_left_to_right = new ScaleAnimation(0,1,1,1);
        animation_left_to_right.setDuration(1100);

        ScaleAnimation animation_right_to_left = new ScaleAnimation(0,1,1,1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_right_to_left.setDuration(1100);
        email=(EditText)findViewById(R.id.login_email);
        password=(EditText)findViewById(R.id.login_password);

        // Shared Preferences
        sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        emailText=sharedPreferences.getString("A","");
        email.setText(emailText);

        passwordText=sharedPreferences.getString("B","");
        password.setText(passwordText);



        imageButton1=(ImageButton)findViewById(R.id.sign_in_with_google);
        imageButton2=(ImageButton)findViewById(R.id.sign_in_with_fb);
        imageButton3=(ImageButton)findViewById(R.id.forgot_password);
        imageButton4=(ImageButton)findViewById(R.id.sign_up);

        imageButton1.startAnimation(animation_left_to_right);
        imageButton3.startAnimation(animation_left_to_right);
        imageButton2.startAnimation(animation_right_to_left);
        imageButton4.startAnimation(animation_right_to_left);




        button_login=(Button)findViewById(R.id.btn_login);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_LoginSIgnupStartPage,new GoogleSignin()).addToBackStack(null).commit();

            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), SignupPage.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                emailText=email.getText().toString().trim();
                passwordText=password.getText().toString().trim();
                if(!validateEmail(emailText)){
                    String message;
                    int color;
                    message = "                  Invalid Email Address";
                    color = Color.RED;
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_login), message, Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    view.setBackgroundColor(color);
                    snackbar.show();
                    return;

                }
                if(passwordText.length() <= 5){
                    String message;
                    int color;
                    message = "   Length of Password should be greater than 5";
                    color = Color.RED;
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_login), message, Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    view.setBackgroundColor(color);
                    snackbar.show();
                    return;
                }
                if(!isNetworkAvailable()) {
                    String message;
                    int color;
                    message = "                  No Internet Connection";
                    color = Color.RED;
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_login), message, Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    view.setBackgroundColor(color);
                    snackbar.show();
                    return;
                }



                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(LoginSignupStartPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    String message;
                                    int color;
                                    try{
                                        message =  task.getException().getLocalizedMessage();
                                    }
                                    catch (NullPointerException e){
                                        message = "     Authentication Failed ! Try Again";
                                    }

                                    color = Color.RED;
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_login), message, Snackbar.LENGTH_LONG);
                                    View view = snackbar.getView();
                                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                                    params.gravity = Gravity.TOP;
                                    view.setLayoutParams(params);
                                    view.setBackgroundColor(color);
                                    snackbar.show();
                                } else {
                                    editor.putString("A",email.getText().toString()).apply();
                                    editor.putString("B",password.getText().toString()).apply();
                                    Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });


    }



     private boolean isNetworkAvailable() {
         ConnectivityManager connectivityManager
                 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
         return activeNetworkInfo != null && activeNetworkInfo.isConnected();
     }

     public boolean validateEmail(String email) {
         Matcher matcher = pattern.matcher(email);
         return matcher.matches();
     }
 }
