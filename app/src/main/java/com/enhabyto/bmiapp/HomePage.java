package com.enhabyto.bmiapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name;
    private EditText text1, text2, text3, text4, text5, text6;
    DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference d_ref;
    private TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4, textInputLayout5;

    private int weight_kg, height_ft, height_in , age, height_cm, weight_lb;
    private Button btn_height, btn_weight, btn_age;

    private int weight_for_bmi, difference_kg, difference_lb, ideal_weight_lb, ideal_weight_kg;
    private float height_for_bmi, temp, bmi_temp, bmi;
    private String region, gender, item1, item2;
    private WaveLoadingView mWaveLoadingView;
    int convert_bmi;
    private ImageView img_chaka, img_bmi_circle;
    Animation anim_anti_rotate, anim_rot, rot_slow;

    TextView regiontext, ideal_weight;
    TextView ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9;
    TextView r1, r2, r3, r4, r5, r6, r7, r8, r9;
    private TextView placeholder, greeting_btn;
    ImageView right_wrong_img, moon_or_sun;
    private Toolbar toolbar;
    private Window window;
    private Spinner spinner, spinner2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        window = getWindow();
        //change notification color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //now change color
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Body Mass Index");

        textInputLayout1 = (TextInputLayout)findViewById(R.id.til1);
        textInputLayout2 = (TextInputLayout)findViewById(R.id.til2);
        textInputLayout3 = (TextInputLayout)findViewById(R.id.til3);
        textInputLayout4 = (TextInputLayout)findViewById(R.id.til4);
        textInputLayout5 = (TextInputLayout)findViewById(R.id.til5);

        text1 = (EditText)findViewById(R.id.home_text1);
        text2 = (EditText) findViewById(R.id.home_text2);
        text3 = (EditText) findViewById(R.id.home_text3);
        text4 = (EditText) findViewById(R.id.home_text4);
        text5 = (EditText) findViewById(R.id.home_text5);
        text6 = (EditText) findViewById(R.id.home_text6);

        btn_height = (Button)findViewById(R.id.Home_btn);
        btn_weight = (Button)findViewById(R.id.Home_btn2);
        btn_age = (Button)findViewById(R.id.Home_btn3);

        setSupportActionBar(toolbar);
        spinner = (Spinner)findViewById(R.id.spinner_height);
        spinner2 = (Spinner)findViewById(R.id.spinner_weight);

        final List<String> categories = new ArrayList<>();
        categories.add("ft+in");
        categories.add("cm");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  item1 = parent.getItemAtPosition(position).toString();
                                                  if(item1.equals("cm")){
                                                    textInputLayout1.setVisibility(View.INVISIBLE);
                                                    textInputLayout2.setVisibility(View.INVISIBLE);
                                                    textInputLayout3.setVisibility(View.VISIBLE);
                                                      text1.setText("");
                                                      text2.setText("");


                                                  }
                                                  else {
                                                      textInputLayout1.setVisibility(View.VISIBLE);
                                                      textInputLayout2.setVisibility(View.VISIBLE);
                                                      textInputLayout3.setVisibility(View.INVISIBLE);
                                                      text3.setText("");
                                                  }
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          });


        final List<String> categories2 = new ArrayList<>();
        categories2.add("Kg");
        categories2.add("lb");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(dataAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent2, View view, int position2, long id) {
                item2 = parent2.getItemAtPosition(position2).toString();
                if(item2.equals("lb")){
                    textInputLayout4.setVisibility(View.INVISIBLE);
                    textInputLayout5.setVisibility(View.VISIBLE);
                    text4.setText("");


                }
                else {
                    textInputLayout4.setVisibility(View.VISIBLE);
                    textInputLayout5.setVisibility(View.INVISIBLE);
                    text5.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ct1 = (TextView) findViewById(R.id.class_text1);
        ct2 = (TextView)findViewById(R.id.class_text2);
        ct3 = (TextView)findViewById(R.id.class_text3);
        ct4 = (TextView)findViewById(R.id.class_text4);
        ct5 = (TextView)findViewById(R.id.class_text5);
        ct6 = (TextView)findViewById(R.id.class_text6);
        ct7 = (TextView)findViewById(R.id.class_text7);
        ct8 = (TextView)findViewById(R.id.class_text8);
        ct9 = (TextView)findViewById(R.id.class_text9);

        r1 = (TextView)findViewById(R.id.place_severe_thin);
        r2 = (TextView)findViewById(R.id.place_moderate_thin);
        r3 = (TextView)findViewById(R.id.place_mild_thin);
        r4 = (TextView)findViewById(R.id.place_normal);
        r5 = (TextView)findViewById(R.id.place_overweight);
        r6 = (TextView)findViewById(R.id.obese);
        r7 = (TextView)findViewById(R.id.place_type1);
        r8 = (TextView)findViewById(R.id.place_type2);
        r9 = (TextView)findViewById(R.id.place_type3);
        ideal_weight = (TextView)findViewById(R.id.ideal_weight_text);
        right_wrong_img  = (ImageView)findViewById(R.id.right_wrong);
        moon_or_sun = (ImageView)findViewById(R.id.moon_sun);
        placeholder = (TextView)findViewById(R.id.placeholder);
        greeting_btn = (TextView)findViewById(R.id.greeting);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ReprineatoRegular.otf");

        regiontext = (TextView)findViewById(R.id.reg_text);
        img_chaka = (ImageView)findViewById(R.id.chaka_bmi);
        img_bmi_circle = (ImageView)findViewById(R.id.bmi_descriptor_circle);

        anim_anti_rotate  = AnimationUtils.loadAnimation(this, R.anim.rotate_anti);
        anim_rot   = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rot_slow   = AnimationUtils.loadAnimation(this, R.anim.rotate_slow);
        img_chaka.startAnimation(anim_anti_rotate);
        img_bmi_circle.startAnimation(anim_rot);

        d_ref = d_parent.child("users").child(user.getUid());
        name = (TextView)findViewById(R.id.disp_name);
        name.setTypeface(typeface);
        greeting_btn.setTypeface(typeface);


        mWaveLoadingView = (WaveLoadingView)findViewById(R.id.waveLoadingView);

        btn_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int h_ft;
                int h_in;
                int h_cm;

                if(item1.equals("cm")){
                    if(TextUtils.isEmpty(text3.getText().toString())){
                        Toast.makeText(HomePage.this, "cm field empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    h_cm = Integer.parseInt(text3.getText().toString());
                    if(h_cm < 61 || h_cm > 275 ){
                        Toast.makeText(HomePage.this, "cm(61 - 275)", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int feetPart = 0;
                    int inchesPart = 0;

                    double dCentimeter = h_cm;
                    feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
                    inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));

                    d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("feet").setValue(feetPart);
                    d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("inches").setValue(inchesPart);
                    d_parent.child("users").child(user.getUid()).child("height").child("centimeter").setValue(h_cm);


                }

                else{

                    if(TextUtils.isEmpty(text1.getText().toString())){
                        Toast.makeText(HomePage.this, "ft field empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(text2.getText().toString())){
                        Toast.makeText(HomePage.this, "in field empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    h_ft = Integer.parseInt(text1.getText().toString());
                        h_in = Integer.parseInt(text2.getText().toString());


                    if(h_ft < 2 || h_ft > 8 || h_in < 0 ||h_in >11){
                        Toast.makeText(HomePage.this, "ft(2'' - 8'') and inch(1' - 11')", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double temp_total_feet, converted_centimeter;
                    int temp;
                    temp_total_feet = (h_ft * 12) + h_in;
                    converted_centimeter = temp_total_feet * 2.54;
                    temp = (int)(converted_centimeter + 0.5d);
                    d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("feet").setValue(h_ft);
                    d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("inches").setValue(h_in);
                    d_parent.child("users").child(user.getUid()).child("height").child("centimeter").setValue(temp);


                }
            }
        });

        btn_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w_kg, w_lb;
                if(item2.equals("lb")){
                    if(TextUtils.isEmpty(text4.getText().toString())){
                        Toast.makeText(HomePage.this, "lb field empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    w_lb = Integer.parseInt(text5.getText().toString());
                    if(w_lb < 10 || w_lb > 882){
                        Toast.makeText(HomePage.this, "recheck weight", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double temp;
                    int converted_kg;
                    temp = w_lb * 0.453592;
                    converted_kg = (int)(temp+0.5d);
                    d_parent.child("users").child(user.getUid()).child("weight").child("kilograms").setValue(converted_kg);
                    d_parent.child("users").child(user.getUid()).child("weight").child("pounds").setValue(w_lb);
                }

                else {

                    if(TextUtils.isEmpty(text5.getText().toString())){
                        Toast.makeText(HomePage.this, "kg field empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    w_kg = Integer.parseInt(text4.getText().toString());

                    if(w_kg < 4 || w_kg > 400){
                        Toast.makeText(HomePage.this, "recheck weight", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    double temp;
                    int converted_lb;
                    temp = w_kg * 2.20462;
                    converted_lb = (int)(temp+0.5d);
                    d_parent.child("users").child(user.getUid()).child("weight").child("pounds").setValue(converted_lb);
                    d_parent.child("users").child(user.getUid()).child("weight").child("kilograms").setValue(w_kg);


                }
            }
        });

        btn_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age;

                    if(TextUtils.isEmpty(text6.getText().toString())) {
                        Toast.makeText(HomePage.this, "age field empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    age = Integer.parseInt(text6.getText().toString());
                    if(age < 2 || age > 120){
                        Toast.makeText(HomePage.this, "recheck age", Toast.LENGTH_SHORT).show();
                       return;
                    }
                d_parent.child("users").child(user.getUid()).child("age").setValue(age);

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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


    public void onStart() {
        super.onStart();
        calculate_bmi();
    }

    private void calculate_bmi(){


        d_ref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d_name = dataSnapshot.child("display_name").getValue(String.class);
                name.setText(String.format("Welcome,\n%s", d_name));
                age = dataSnapshot.child("age").getValue(Integer.class);
                height_ft = dataSnapshot.child("height").child("feet_and_inches").child("feet").getValue(Integer.class);
                height_in = dataSnapshot.child("height").child("feet_and_inches").child("inches").getValue(Integer.class);
                height_cm = dataSnapshot.child("height").child("centimeter").getValue(Integer.class);
                weight_kg = dataSnapshot.child("weight").child("kilograms").getValue(Integer.class);
                weight_lb = dataSnapshot.child("weight").child("pounds").getValue(Integer.class);
                gender = dataSnapshot.child("gender").getValue(String.class);
                region = dataSnapshot.child("region").getValue(String.class);
                regiontext.setText(region);

                int total_inches = (height_ft*12) + height_in;



                RelativeLayout layout =(RelativeLayout)findViewById(R.id.fragment_container_home_page);

                if(gender.equals("male")){
                    layout.setBackgroundResource(R.color.lightBlue);
                    if(height_cm >= 152){
                        ideal_weight_lb = 106 + (6 * (total_inches - 60));
                        double temp_weight;

                        temp_weight = ideal_weight_lb * 0.453592;
                        ideal_weight_kg = (int)(temp_weight+0.5d);


                        ideal_weight.setText(ideal_weight_kg +" kg / " + ideal_weight_lb+" lb");

                    }
                    else {
                        ideal_weight_lb = (106 - ((height_ft-1) * 3) + 9 * 6 );
                        double temp_weight;
                        temp_weight = ideal_weight_lb * 0.453592;
                        ideal_weight_kg = (int)(temp_weight+0.5d);
                        ideal_weight.setText(ideal_weight_kg +" kg / " + ideal_weight_lb+" lb");
                    }

                }
                else {
                    layout.setBackgroundResource(R.color.lightPink);

                    if(height_cm >= 152){
                        ideal_weight_lb = 100 + (5 * (total_inches - 60));
                        double temp_weight;
                        temp_weight = ideal_weight_lb * 0.453592;
                        ideal_weight_kg = (int)(temp_weight+0.5d);
                        ideal_weight.setText(ideal_weight_kg +" kg / " + ideal_weight_lb+" lb");
                    }
                    else {
                        ideal_weight_lb = (100 - ((height_ft-1) * 3) + 9 * 5 );
                        double temp_weight;
                        temp_weight = ideal_weight_lb * 0.453592;
                        ideal_weight_kg = (int)(temp_weight+0.5d);
                        ideal_weight.setText(ideal_weight_kg +" kg / " + ideal_weight_lb+" lb");
                    }
                }




                int hours = new Time(System.currentTimeMillis()).getHours();

                if(hours >= 5 && hours < 12){
                    greeting_btn.setText("Good Morning");
                    moon_or_sun.setBackgroundResource(R.drawable.sun);
                    moon_or_sun.startAnimation(rot_slow);

                }else if(hours >= 12 && hours < 18){
                    greeting_btn.setText("Good Afternoon");
                    moon_or_sun.setBackgroundResource(R.drawable.sun);
                    moon_or_sun.startAnimation(rot_slow);

                }else if(hours >= 18 && hours < 22){
                    greeting_btn.setText("Good Evening");
                    moon_or_sun.setBackgroundResource(R.drawable.moon);
                    moon_or_sun.startAnimation(rot_slow);

                }else {
                    greeting_btn.setText("Sweet Dreams");
                    moon_or_sun.setBackgroundResource(R.drawable.moon);
                    moon_or_sun.startAnimation(rot_slow);
                }


                weight_for_bmi = weight_kg;
                height_for_bmi = height_cm / 100f;
                temp = (height_for_bmi * height_for_bmi);
                bmi = (weight_for_bmi) / temp;


                switch (region){

                    case "ASIA" :
                        r3.setText("17 - 18.5");
                        r4.setText("18.6 - 24.9");
                        r5.setText("25 - 29.9");
                        r6.setText("30 - 34.9");
                        break;

                    case "SOUTH AMERICA":
                        r3.setText("17 - 19");
                        r4.setText("19 - 24.9");
                        r5.setText("25 - 29.9");
                        r6.setText("30 - 34.9");
                        break;

                    default:
                        r3.setText("17 - 18.5");
                        r4.setText("18.6 - 24.9");
                        r5.setText("25 - 29.9");
                        r6.setText("30 - 34.9");
                }




                convert_bmi = (int)(bmi);
            //   String.format("%.1f", bmi)

  //------------------------------- for Asia  --------------------------------------------------

                if(region.equals("ASIA")){
                    if(bmi < 16){
                    mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                    mWaveLoadingView.setProgressValue(convert_bmi);
                    img_chaka.startAnimation(anim_anti_rotate);
                    img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightMinus();


                        changeAll();
                       change1();


                    underWeight();
                    }
                if(bmi >= 16 && bmi <17){


                    mWaveLoadingView.setCenterTitle(String.format("%.1f", bmi));
                    img_chaka.startAnimation(anim_anti_rotate);
                    img_bmi_circle.startAnimation(anim_rot);
                    differenceInWeightMinus();
                    changeAll();
                    change2();
                    underWeight();
                }
                if(bmi >=17 && bmi < 18.5) {

                    mWaveLoadingView.setProgressValue(convert_bmi);
                    mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                    img_chaka.startAnimation(anim_anti_rotate);
                    img_bmi_circle.startAnimation(anim_rot);

                    changeAll();
                    change3();
                    underWeight();

                }

                 if(bmi >=18.5 && bmi <23){

                 mWaveLoadingView.setProgressValue(convert_bmi);
                 mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                 img_chaka.startAnimation(anim_anti_rotate);
                 img_bmi_circle.startAnimation(anim_rot);

                     differenceInWeightPlus();
                     changeAll();
                     change4();
                     normalWeight();

                       }
                 if(bmi >=23 && bmi <30){

                 mWaveLoadingView.setProgressValue(convert_bmi);
                 mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                 img_chaka.startAnimation(anim_anti_rotate);
                 img_bmi_circle.startAnimation(anim_rot);
                     differenceInWeightPlus();
                     changeAll();
                     change5();

                     overWeight();
                      }
                if(bmi >=30 && bmi <35){

                 mWaveLoadingView.setProgressValue(convert_bmi);
                 mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                    img_chaka.startAnimation(anim_anti_rotate);
                    img_bmi_circle.startAnimation(anim_rot);
                    differenceInWeightPlus();
                    changeAll();
                    change6();

                    obese();
                                      }
                    if(bmi >=35 && bmi <40){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightPlus();
                        changeAll();
                        change7();

                        extremeObese();
                    }
                    if(bmi >=40 && bmi <50){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightPlus();
                        changeAll();
                        change8();
                        extremeObese();
                    }
                    if(bmi >=50){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_bmi_circle.setBackgroundResource(R.drawable.obese);
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightPlus();
                        changeAll();
                        change9();

                        extremeObese();
                    }

             }

//-------------------------- for south america  -----------------------------------------------------

             else if(region.equals("South America")){
                    if(bmi < 16){
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        mWaveLoadingView.setProgressValue(convert_bmi);
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightMinus();
                        changeAll();
                        change1();

                        underWeight();
                    }
                    if(bmi >= 16 && bmi <17){
                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightMinus();
                        changeAll();
                        change2();

                        underWeight();
                    }
                    if(bmi >=17 && bmi < 19) {

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        changeAll();
                        change3();
                        differenceInWeightMinus();
                        underWeight();

                    }

                    if(bmi >= 19 && bmi < 25){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        changeAll();
                        change4();

                        normalWeight();

                    }
                    if(bmi >=25 && bmi <30){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        changeAll();
                        change5();
                        differenceInWeightPlus();
                        overWeight();
                    }
                    if(bmi >=30 && bmi <35){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightPlus();
                        changeAll();
                        change6();

                        obese();
                    }
                    if(bmi >=35 && bmi <40){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightPlus();
                        changeAll();
                        change7();

                        extremeObese();
                    }
                    if(bmi >=40 && bmi <50){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightPlus();
                        changeAll();
                        change8();

                        extremeObese();
                    }
                    if(bmi >=50){

                        mWaveLoadingView.setProgressValue(convert_bmi);
                        mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                        img_chaka.startAnimation(anim_anti_rotate);
                        img_bmi_circle.startAnimation(anim_rot);
                        differenceInWeightPlus();
                        changeAll();
                        change9();

                        extremeObese();
                    }

//---------------------------------- for rest of the regions  -------------------------------------------

                    else{
                        if(bmi < 16){
                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            mWaveLoadingView.setProgressValue(convert_bmi);
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            differenceInWeightMinus();
                            changeAll();
                            change1();

                            underWeight();
                        }
                        if(bmi >= 16 && bmi <17){


                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            differenceInWeightMinus();
                            changeAll();
                            change2();

                            underWeight();
                        }
                        if(bmi >=17 && bmi < 18.5) {

                            mWaveLoadingView.setProgressValue(convert_bmi);
                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            changeAll();
                            change3();
                            differenceInWeightMinus();
                            underWeight();

                        }

                        if(bmi >=18.5 && bmi <25){

                            mWaveLoadingView.setProgressValue(convert_bmi);
                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            changeAll();
                            change4();

                            normalWeight();

                        }
                        if(bmi >=25 && bmi <30){

                            mWaveLoadingView.setProgressValue(convert_bmi);
                            mWaveLoadingView.setCenterTitle("Overweight");
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            changeAll();
                            change5();
                            differenceInWeightPlus();
                            overWeight();
                        }
                        if(bmi >=30 && bmi <35){

                            mWaveLoadingView.setProgressValue(convert_bmi);
                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            differenceInWeightPlus();
                            changeAll();
                            change6();

                            obese();
                        }
                        if(bmi >=35 && bmi <40){

                            mWaveLoadingView.setProgressValue(convert_bmi);
                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            differenceInWeightPlus();
                            changeAll();
                            change7();

                            extremeObese();
                        }
                        if(bmi >=40 && bmi <50){

                            mWaveLoadingView.setProgressValue(convert_bmi);
                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            differenceInWeightPlus();
                            changeAll();
                            change8();

                            extremeObese();
                        }
                        if(bmi >=50){

                            mWaveLoadingView.setProgressValue(convert_bmi);
                            mWaveLoadingView.setCenterTitle("BMI "+String.format("%.1f", bmi));
                            img_chaka.startAnimation(anim_anti_rotate);
                            img_bmi_circle.startAnimation(anim_rot);
                            differenceInWeightPlus();
                            changeAll();
                            change9();

                            extremeObese();
                        }
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void underWeight(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(ContextCompat.getColor(this, R.color.redWrong));
        mWaveLoadingView.setBorderColor(ContextCompat.getColor(this, R.color.redWrong));
        img_bmi_circle.setBackgroundResource(R.drawable.overweight_underweight);
        name.setBackgroundResource(R.drawable.rounded_background_redwrong);
        window.setStatusBarColor(ContextCompat.getColor(HomePage.this,R.color.redWrong));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.redWrong));
        mWaveLoadingView.setAnimDuration(3000);

        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void normalWeight(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(ContextCompat.getColor(this, R.color.greenRight));
        mWaveLoadingView.setBorderColor(ContextCompat.getColor(this, R.color.greenRight));
        img_bmi_circle.setBackgroundResource(R.drawable.normal);
        name.setBackgroundResource(R.drawable.rounded_background_greenright);
        window.setStatusBarColor(ContextCompat.getColor(HomePage.this,R.color.greenRight));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.greenRight));
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   private void overWeight(){
       mWaveLoadingView.setAmplitudeRatio(60);
       mWaveLoadingView.setWaveColor(ContextCompat.getColor(this, R.color.redWrong));
       mWaveLoadingView.setBorderColor(ContextCompat.getColor(this, R.color.redWrong));
       img_bmi_circle.setBackgroundResource(R.drawable.overweight_underweight);
       name.setBackgroundResource(R.drawable.rounded_background_redwrong);
       window.setStatusBarColor(ContextCompat.getColor(HomePage.this,R.color.redWrong));
       toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.redWrong));
       mWaveLoadingView.setAnimDuration(3000);
       mWaveLoadingView.pauseAnimation();
       mWaveLoadingView.resumeAnimation();
       mWaveLoadingView.cancelAnimation();
       mWaveLoadingView.startAnimation();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void obese(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(ContextCompat.getColor(this, R.color.redWrong));
        mWaveLoadingView.setBorderColor(ContextCompat.getColor(this, R.color.redWrong));
        img_bmi_circle.setBackgroundResource(R.drawable.overweight_underweight);
        name.setBackgroundResource(R.drawable.rounded_background_redwrong);
        window.setStatusBarColor(ContextCompat.getColor(HomePage.this,R.color.redWrong));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.redWrong));
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void extremeObese(){
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(ContextCompat.getColor(this, R.color.redWrong));
        mWaveLoadingView.setBorderColor(ContextCompat.getColor(this, R.color.redWrong));
        img_bmi_circle.setBackgroundResource(R.drawable.overweight_underweight);
        name.setBackgroundResource(R.drawable.rounded_background_redwrong);
        window.setStatusBarColor(ContextCompat.getColor(HomePage.this,R.color.redWrong));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.redWrong));
        mWaveLoadingView.setAnimDuration(3000);
        mWaveLoadingView.pauseAnimation();
        mWaveLoadingView.resumeAnimation();
        mWaveLoadingView.cancelAnimation();
        mWaveLoadingView.startAnimation();

    }

    private void changeAll(){
        ct1.setTextColor(Color.BLACK);
        r1.setTextColor(Color.BLACK);
        ct1.setTypeface(Typeface.DEFAULT);
        r1.setTypeface(Typeface.DEFAULT);

        ct2.setTextColor(Color.BLACK);
        r2.setTextColor(Color.BLACK);
        ct2.setTypeface(Typeface.DEFAULT);
        r2.setTypeface(Typeface.DEFAULT);

        ct3.setTextColor(Color.BLACK);
        r3.setTextColor(Color.BLACK);
        ct3.setTypeface(Typeface.DEFAULT);
        r3.setTypeface(Typeface.DEFAULT);

        ct4.setTextColor(Color.BLACK);
        r4.setTextColor(Color.BLACK);
        ct4.setTypeface(Typeface.DEFAULT);
        r4.setTypeface(Typeface.DEFAULT);

        ct5.setTextColor(Color.BLACK);
        r5.setTextColor(Color.BLACK);
        ct5.setTypeface(Typeface.DEFAULT);
        r5.setTypeface(Typeface.DEFAULT);

        ct6.setTextColor(Color.BLACK);
        r6.setTextColor(Color.BLACK);
        ct6.setTypeface(Typeface.DEFAULT);
        r6.setTypeface(Typeface.DEFAULT);

        ct7.setTextColor(Color.BLACK);
        r7.setTextColor(Color.BLACK);
        ct7.setTypeface(Typeface.DEFAULT);
        r7.setTypeface(Typeface.DEFAULT);

        ct8.setTextColor(Color.BLACK);
        r8.setTextColor(Color.BLACK);
        ct8.setTypeface(Typeface.DEFAULT);
        r8.setTypeface(Typeface.DEFAULT);

        ct9.setTextColor(Color.BLACK);
        r9.setTextColor(Color.BLACK);
        ct9.setTypeface(Typeface.DEFAULT);
        r9.setTypeface(Typeface.DEFAULT);

    }
    private void change1(){
        ct1.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r1.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct1.setTypeface(Typeface.DEFAULT_BOLD);
        r1.setTypeface(Typeface.DEFAULT_BOLD);

        placeholder.setText("-" +difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));


    }
    private void change2(){
        ct2.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r2.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct2.setTypeface(Typeface.DEFAULT_BOLD);
        r2.setTypeface(Typeface.DEFAULT_BOLD);

        placeholder.setText("-" +difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(Color.RED);
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));

    }

    private void change3(){
        ct3.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r3.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct3.setTypeface(Typeface.DEFAULT_BOLD);
        r3.setTypeface(Typeface.DEFAULT_BOLD);

        placeholder.setText("-" +difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));

    }
    private void change4(){
        ct4.setTextColor(ContextCompat.getColor(this, R.color.greenRight));
        r4.setTextColor(ContextCompat.getColor(this, R.color.greenRight));
        ct4.setTypeface(Typeface.DEFAULT_BOLD);
        r4.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setText("Normal");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.greenRight));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.right);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));

    }
    private void change5(){
        ct5.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r5.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct5.setTypeface(Typeface.DEFAULT_BOLD);
        r5.setTypeface(Typeface.DEFAULT_BOLD);

        placeholder.setText("+" +difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));

    }
    private void change6(){
        ct6.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r6.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct6.setTypeface(Typeface.DEFAULT_BOLD);
        r6.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setText("+" +difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));

    }
    private void change7(){
        ct7.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r7.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct7.setTypeface(Typeface.DEFAULT_BOLD);
        r7.setTypeface(Typeface.DEFAULT_BOLD);

        placeholder.setText("+" +difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));
    }
    private void change8(){
        ct8.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r8.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct8.setTypeface(Typeface.DEFAULT_BOLD);
        r8.setTypeface(Typeface.DEFAULT_BOLD);

        placeholder.setText("+" +difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);
        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));

    }

    private void change9(){
        ct9.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        r9.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        ct9.setTypeface(Typeface.DEFAULT_BOLD);
        r9.setTypeface(Typeface.DEFAULT_BOLD);

        placeholder.setText("+"+difference_kg +" kg / "+ difference_lb +" lb");
        placeholder.setTextColor(ContextCompat.getColor(this, R.color.redWrong));
        placeholder.setTypeface(Typeface.DEFAULT_BOLD);
        placeholder.setTextSize(20);

        right_wrong_img.setBackgroundResource(R.drawable.wrong);
        ideal_weight.setTextColor(ContextCompat.getColor(this, R.color.greenRight));
    }


    private void differenceInWeightMinus(){
        difference_kg = ideal_weight_kg - weight_kg;
        difference_lb = ideal_weight_lb - weight_lb;
    }

   private void differenceInWeightPlus(){
        difference_kg = weight_kg - ideal_weight_kg;
        difference_lb = weight_lb - ideal_weight_lb;
    }


}
