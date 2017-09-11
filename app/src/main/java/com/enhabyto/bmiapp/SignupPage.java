package com.enhabyto.bmiapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPage extends AppCompatActivity {

    RoundedImageView imageButton1, imageButton2;
    EditText editText1, editText2;
    Button button;
    private FirebaseAuth auth;
    private String emailText, passwordText;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();

        }
        catch (NullPointerException e){
            Toast.makeText(this, "Null Pointer Exception", Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_signup_page);

        Window window = getWindow();
        //change notification color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //now change color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SignupPage.this,R.color.red));
        }

        ScaleAnimation animation_left_to_right = new ScaleAnimation(0,1,1,1);
        animation_left_to_right.setDuration(1100);

        ScaleAnimation animation_right_to_left = new ScaleAnimation(0,1,1,1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_right_to_left.setDuration(1100);

        imageButton1 = (RoundedImageView) findViewById(R.id.imageView1);
        imageButton2 = (RoundedImageView) findViewById(R.id.imageView2);
        editText1=(EditText)findViewById(R.id.login_email);
        editText2=(EditText)findViewById(R.id.login_password);
        button=(Button)findViewById(R.id.btn_reg);
        auth = FirebaseAuth.getInstance();

        imageButton1.startAnimation(animation_left_to_right);
        imageButton2.startAnimation(animation_right_to_left);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ReprineatoRegular.otf");
        editText1.setTypeface(typeface);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailText=editText1.getText().toString().trim();
                passwordText=editText2.getText().toString().trim();


                    if(!validateEmail(emailText)){
                    String message;
                    int color;
                    message = "Invalid Email Address";
                    color = Color.RED;
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_reg), message, Snackbar.LENGTH_LONG);
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
                    message = "Length of Password should be greater than 5";
                    color = Color.RED;
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_reg), message, Snackbar.LENGTH_LONG);
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
                    message = "No Internet Connection";
                    color = Color.RED;
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_reg), message, Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    view.setBackgroundColor(color);
                    snackbar.show();
                    return;
                }
                emailText=editText1.getText().toString().trim();
                passwordText=editText2.getText().toString().trim();

                auth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupPage.this, "Authentication failed. " + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    String message;
                                    try{
                                        message = task.getException().getLocalizedMessage();
                                    }
                                    catch (NullPointerException e){
                                        message="Account Created Successfully";
                                    }

                                    Toast.makeText(SignupPage.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginSignupStartPage.class);
                                    startActivity(intent);
                                }
                            }
                        });

            }
        });


        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_SignupPage,new GoogleSignup()).addToBackStack(null).commit();

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
            String message;
            int color;
            message = "No Internet Connection";
            color = Color.RED;
            Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_reg), message, Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            view.setBackgroundColor(color);
            snackbar.show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}
