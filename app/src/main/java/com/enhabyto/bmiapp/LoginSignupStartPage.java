 package com.enhabyto.bmiapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




 public class LoginSignupStartPage extends AppCompatActivity {

     protected EditText email, password;
     RoundedImageView imageButton1, imageButton2, imageButton3, imageButton4;
     protected Button button_login;
     private FirebaseAuth mAuth;
     private String TAG = "tag message ";
     private String emailText, passwordText;
     private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
     private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

     private int num = 2;

     CircularProgressBar circularProgressBar;
     ImageButton forgot_icon;

     DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
     DatabaseReference d_ref_database;



     private SharedPreferences sharedPreferences;
     private SharedPreferences.Editor editor;

     private  ScaleAnimation animation_left_to_right;
     private ScaleAnimation animation_right_to_left;

     private ImageView app_logo;
     Animation animBlink, fadeIn, animRot;





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

        //now change color
            window.setStatusBarColor(ContextCompat.getColor(LoginSignupStartPage.this,R.color.red));
        }

        app_logo = (ImageView)findViewById(R.id.imageViewAppLogo);
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animRot = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);



        animation_left_to_right = new ScaleAnimation(0,1,1,1);
        animation_left_to_right.setDuration(1100);

        animation_right_to_left = new ScaleAnimation(0,1,1,1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_right_to_left.setDuration(1100);
        email=(EditText)findViewById(R.id.login_email);
        password=(EditText)findViewById(R.id.login_password);

        forgot_icon = (ImageButton)findViewById(R.id.forgot_pass_icon);
        forgot_icon.startAnimation(animRot);
        circularProgressBar = (CircularProgressBar)findViewById(R.id.login_progressbar);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.red));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_grey_700));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.width_pb));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.width_load_pb));

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ReprineatoRegular.otf");
        email.setTypeface(typeface);

        // Shared Preferences
        sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        emailText=sharedPreferences.getString("A","");
        email.setText(emailText);

        passwordText=sharedPreferences.getString("B","");
        password.setText(passwordText);



        imageButton1=(RoundedImageView) findViewById(R.id.sign_in_with_google);
        imageButton2=(RoundedImageView) findViewById(R.id.sign_in_with_fb);
        imageButton3=(RoundedImageView) findViewById(R.id.forgot_password);
        imageButton4=(RoundedImageView) findViewById(R.id.sign_up);

        imageButton1.startAnimation(animation_left_to_right);
        imageButton2.startAnimation(animation_right_to_left);
        imageButton4.startAnimation(animation_right_to_left);

        button_login=(Button)findViewById(R.id.btn_login);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_LoginSIgnupStartPage,new GoogleSignin()).addToBackStack(null).commit();

            }
        });

      imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), SignupPage.class);
                startActivity(intent);
            }
        });

        forgot_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num == 2 ){
                    imageButton3.setVisibility(View.VISIBLE);
                    imageButton3.startAnimation(animation_right_to_left);
                    num++;
                    forgot_icon.setVisibility(View.GONE);
                }
            }
        });





        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                emailText=email.getText().toString().trim();
                passwordText=password.getText().toString().trim();
                if(!validateEmail(emailText)){
                    email.startAnimation(animBlink);
                    String message;
                    int color;
                    message = "Invalid Email Address";
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
                    password.startAnimation(animBlink);
                    String message;
                    int color;
                    message = "Length of Password should be greater than 5";
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
                    app_logo.startAnimation(animBlink);
                    String message;
                    int color;
                    message = "No Internet Connection";
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
                                        message = "Authentication Failed ! Try Again";
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
                                    circularProgressBar.setVisibility(View.VISIBLE);
                                    int animationDuration = 2500; // 2500ms = 2,5s
                                    circularProgressBar.setProgressWithAnimation(65, animationDuration); // Default duration = 1500ms
                                    editor.putString("A",email.getText().toString()).apply();
                                    editor.putString("B",password.getText().toString()).apply();

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    d_ref_database = d_parent.child("users").child(user.getUid()).child("flag");
                                    // Toast.makeText(getActivity(), user.getProviderId(), Toast.LENGTH_SHORT).show();


                                    d_ref_database.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {


                                            String checker = dataSnapshot.getValue(String.class);
                                            if (checker == null) {
                                                Intent intent = new Intent(LoginSignupStartPage.this, UserInfo.class);
                                                startActivity(intent);
                                                finish();
                                                return;
                                            }

                                            if (!checker.equals("confirmed")) {
                                                Intent intent = new Intent(LoginSignupStartPage.this, UserInfo.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(LoginSignupStartPage.this, HomePage.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Toast.makeText(getActivity(), "Database server error", Toast.LENGTH_SHORT).show();
                                        }
                                    });


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

     protected void onStart(){
         super.onStart();
         if(!isNetworkAvailable()) {
             app_logo.startAnimation(animBlink);
             String message;
             int color;
             message = "No Internet Connection";
             color = Color.RED;
             Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_login), message, Snackbar.LENGTH_LONG);
             View view = snackbar.getView();
             FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
             params.gravity = Gravity.TOP;
             view.setLayoutParams(params);
             view.setBackgroundColor(color);
             snackbar.show();
         }
     }

 }
