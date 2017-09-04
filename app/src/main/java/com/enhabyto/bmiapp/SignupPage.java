package com.enhabyto.bmiapp;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
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
    private Matcher matcher;

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




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailText=editText1.getText().toString().trim();
                passwordText=editText2.getText().toString().trim();
                if(!validateEmail(emailText)){
                    String message;
                    int color;
                    message = "                  Invalid Email Address";
                    color = Color.BLACK;
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
                    message = "   Length of Password should be greater than 5";
                    color = Color.BLACK;
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
                                Toast.makeText(SignupPage.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupPage.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    //finish();
                                    Toast.makeText(SignupPage.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
