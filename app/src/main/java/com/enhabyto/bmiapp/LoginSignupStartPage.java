 package com.enhabyto.bmiapp;


import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


 public class LoginSignupStartPage extends AppCompatActivity {

     private EditText email, password;
     private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
     private Button button_login;


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
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //now change color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(LoginSignupStartPage.this,R.color.red));
        }

        ScaleAnimation animation_left_to_right = new ScaleAnimation(0,1,1,1);
        animation_left_to_right.setDuration(1100);

        ScaleAnimation animation_right_to_left = new ScaleAnimation(0,1,1,1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_right_to_left.setDuration(1100);



        imageButton1=(ImageButton)findViewById(R.id.sign_in_with_google);
        imageButton2=(ImageButton)findViewById(R.id.sign_in_with_fb);
        imageButton3=(ImageButton)findViewById(R.id.forgot_password);
        imageButton4=(ImageButton)findViewById(R.id.sign_up);

        imageButton1.startAnimation(animation_left_to_right);
        imageButton3.startAnimation(animation_left_to_right);
        imageButton2.startAnimation(animation_right_to_left);
        imageButton4.startAnimation(animation_right_to_left);



        email=(EditText)findViewById(R.id.login_email);
        password=(EditText)findViewById(R.id.login_password);
        button_login=(Button)findViewById(R.id.btn_login);

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), SignupPage.class);
                startActivity(intent);
            }
        });


    }



}
