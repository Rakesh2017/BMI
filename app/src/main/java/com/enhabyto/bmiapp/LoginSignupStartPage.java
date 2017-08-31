 package com.enhabyto.bmiapp;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

 public class LoginSignupStartPage extends AppCompatActivity {

     EditText email, password;
     ImageButton imageButton1, imageButton2, imageButton3, imageButton4;


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
        setContentView(R.layout.activity_login_signup_start_page);
        Window window = getWindow();
            //change notification color
            // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //now change color
        window.setStatusBarColor(ContextCompat.getColor(LoginSignupStartPage.this,R.color.red));

        ScaleAnimation animation_left_to_right = new ScaleAnimation(0,1,1,1);
        animation_left_to_right.setDuration(1000);

        ScaleAnimation animation_right_to_left = new ScaleAnimation(0,1,1,1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_right_to_left.setDuration(1000);



        imageButton1=(ImageButton)findViewById(R.id.sign_in_with_google);
        imageButton2=(ImageButton)findViewById(R.id.sign_in_with_fb);
        imageButton3=(ImageButton)findViewById(R.id.forgot_password);
        imageButton4=(ImageButton)findViewById(R.id.sign_up);

        imageButton1.startAnimation(animation_left_to_right);
        imageButton3.startAnimation(animation_left_to_right);
        imageButton2.startAnimation(animation_right_to_left);
        imageButton4.startAnimation(animation_right_to_left);



        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);


    }


}
