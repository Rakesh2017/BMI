package com.enhabyto.bmiapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.itangqi.waveloadingview.WaveLoadingView;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name, agetx, heighttx, weighttx;
    DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference d_ref;

    private int weight_kg, height_ft, height_in , age, height_cm, weight_lb;
    private Button btn_age, btn_height, btn_weight;

    private int weight_for_bmi;
    private float height_for_bmi, temp, bmi_temp, bmi;
    private String region;
    private WaveLoadingView mWaveLoadingView;
    int convert_bmi;
    private ImageView img_chaka, img_bmi_circle;
    Animation anim_anti_rotate, anim_rot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Window window = getWindow();
        //change notification color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //now change color
            window.setStatusBarColor(ContextCompat.getColor(HomePage.this,R.color.red));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Body Mass Index");
        setSupportActionBar(toolbar);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ReprineatoRegular.otf");


        heighttx = (TextView)findViewById(R.id.u_height_text);
        weighttx = (TextView)findViewById(R.id.u_weight_text);
        btn_height = (Button)findViewById(R.id.change_height);
        btn_weight = (Button)findViewById(R.id.change_weight);
        img_chaka = (ImageView)findViewById(R.id.chaka_bmi);
        img_bmi_circle = (ImageView)findViewById(R.id.bmi_descriptor_circle);

        anim_anti_rotate  = AnimationUtils.loadAnimation(this, R.anim.rotate_anti);
        anim_rot   = AnimationUtils.loadAnimation(this, R.anim.rotate);
        img_chaka.startAnimation(anim_anti_rotate);
        img_bmi_circle.startAnimation(anim_rot);

        d_ref = d_parent.child("users").child(user.getUid());
        name = (TextView)findViewById(R.id.disp_name);
        name.setTypeface(typeface);
        heighttx.setTypeface(typeface);
        weighttx.setTypeface(typeface);

        mWaveLoadingView = (WaveLoadingView)findViewById(R.id.waveLoadingView);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        btn_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home_page,new changeHeight()).addToBackStack(null).commit();
                btn_weight.setVisibility(View.INVISIBLE);
                btn_height.setVisibility(View.INVISIBLE);

            }
        });

        btn_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home_page,new changeWeight()).addToBackStack(null).commit();
                btn_weight.setVisibility(View.INVISIBLE);
                btn_height.setVisibility(View.INVISIBLE);

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btn_weight.setVisibility(View.VISIBLE);
        btn_height.setVisibility(View.VISIBLE);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onStart(){
        super.onStart();

        d_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d_name = dataSnapshot.child("display_name").getValue(String.class);
                name.setText(String.format("Hi %s", d_name));
                age = dataSnapshot.child("age").getValue(Integer.class);
                height_ft = dataSnapshot.child("height").child("feet_and_inches").child("feet").getValue(Integer.class);
                height_in = dataSnapshot.child("height").child("feet_and_inches").child("inches").getValue(Integer.class);
                height_cm = dataSnapshot.child("height").child("centimeter").getValue(Integer.class);
                weight_kg = dataSnapshot.child("weight").child("kilograms").getValue(Integer.class);
                weight_lb = dataSnapshot.child("weight").child("pounds").getValue(Integer.class);
                region = dataSnapshot.child("region").getValue(String.class);


                heighttx.setText("You are "+ height_ft+"'' " +height_in+"' or "+ height_cm +" cm tall");
                weighttx.setText("You are "+ weight_kg+" kg or "+ weight_lb +" pounds heavy");

                weight_for_bmi = weight_kg;
                height_for_bmi = height_cm / 100f;
                temp = (height_for_bmi * height_for_bmi);
                bmi = (weight_for_bmi) / temp;


                convert_bmi = (int)(bmi);
            //   String.format("%.1f", bmi))+
                if(region.equals("asia")){
                    if(bmi < 16){
                    mWaveLoadingView.setCenterTitle("Severely Thin");
                    mWaveLoadingView.setProgressValue(convert_bmi);

                        img_bmi_circle.setBackgroundResource(R.drawable.underweight);
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);

                        underWeight();
                    }
                if(bmi >= 16 && bmi <17){


                    mWaveLoadingView.setCenterTitle("Moderately Thin");
                    img_bmi_circle.setBackgroundResource(R.drawable.underweight);
                    img_chaka.startAnimation(anim_anti_rotate);
                    img_bmi_circle.startAnimation(anim_rot);

                    underWeight();
                }
                if(bmi >=17 && bmi < 18.5) {

                    mWaveLoadingView.setProgressValue(convert_bmi);
                    mWaveLoadingView.setCenterTitle("Mild Thin");
                    img_bmi_circle.setBackgroundResource(R.drawable.underweight);
                    img_chaka.startAnimation(anim_anti_rotate);
                    img_bmi_circle.startAnimation(anim_rot);

                    underWeight();

                }

                 if(bmi >=18.5 && bmi <23){

                 mWaveLoadingView.setProgressValue(convert_bmi);
                 mWaveLoadingView.setCenterTitle("Normal Weight");
                     img_bmi_circle.setBackgroundResource(R.drawable.normal);
                     img_chaka.startAnimation(anim_anti_rotate);
                     img_bmi_circle.startAnimation(anim_rot);

                     normalWeight();

                       }
                 if(bmi >=23 && bmi <30){

                 mWaveLoadingView.setProgressValue(convert_bmi);
                 mWaveLoadingView.setCenterTitle("Overweight");
                     img_bmi_circle.setBackgroundResource(R.drawable.overweight);
                     img_chaka.startAnimation(anim_anti_rotate);
                     img_bmi_circle.startAnimation(anim_rot);

                     overWeight();
                      }
                if(bmi >=30 && bmi <35){

                 mWaveLoadingView.setProgressValue(convert_bmi);
                 mWaveLoadingView.setCenterTitle("Obese");
                    img_bmi_circle.setBackgroundResource(R.drawable.obese);
                    img_chaka.startAnimation(anim_anti_rotate);
                    img_bmi_circle.startAnimation(anim_rot);

                    obese();
                                      }
                    if(bmi >=35 && bmi <40){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("Mild Obese");
                        img_bmi_circle.setBackgroundResource(R.drawable.obese);
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);

                        extremeObese();
                    }
                    if(bmi >=40 && bmi <50){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("Moderately Obese");
                        img_bmi_circle.setBackgroundResource(R.drawable.obese);
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);

                        extremeObese();
                    }
                    if(bmi >=50){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("Severely Obese");
                        img_bmi_circle.setBackgroundResource(R.drawable.obese);
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);

                        extremeObese();
                    }

             }

   }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private void underWeight(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.BLUE);
        mWaveLoadingView.setBorderColor(Color.BLUE);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();
    }

    private void normalWeight(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.GREEN);
        mWaveLoadingView.setBorderColor(Color.GREEN);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }

   private void overWeight(){
       mWaveLoadingView.setAmplitudeRatio(60);
       mWaveLoadingView.setWaveColor(Color.YELLOW);
       mWaveLoadingView.setBorderColor(Color.YELLOW);
       mWaveLoadingView.setAnimDuration(3000);
       mWaveLoadingView.pauseAnimation();
       mWaveLoadingView.resumeAnimation();
       mWaveLoadingView.cancelAnimation();
       mWaveLoadingView.startAnimation();

    }
    private void obese(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.RED);
        mWaveLoadingView.setBorderColor(Color.RED);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }
    private void extremeObese(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.RED);
        mWaveLoadingView.setBorderColor(Color.RED);
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }

}
