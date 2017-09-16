package com.enhabyto.bmiapp;

import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name, agetx, heighttx, weighttx;
    DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference d_ref;

    private String gender;
    private int weight_kg, height_ft, height_in , age, height_cm, weight_lb;
    private Button btn_age, btn_height, btn_weight;




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

        agetx = (TextView)findViewById(R.id.u_age_text);
        heighttx = (TextView)findViewById(R.id.u_height_text);
        weighttx = (TextView)findViewById(R.id.u_weight_text);
        btn_age = (Button)findViewById(R.id.change_age);
        btn_height = (Button)findViewById(R.id.change_height);
        btn_weight = (Button)findViewById(R.id.change_weight);

        d_ref = d_parent.child("users").child(user.getUid());
        name = (TextView)findViewById(R.id.disp_name);
        name.setTypeface(typeface);
        agetx.setTypeface(typeface);
        heighttx.setTypeface(typeface);
        weighttx.setTypeface(typeface);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home_page,new changeAge()).addToBackStack(null).commit();
                btn_weight.setVisibility(View.INVISIBLE);
                btn_height.setVisibility(View.INVISIBLE);
                btn_age.setVisibility(View.INVISIBLE);
            }
        });

        btn_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home_page,new changeHeight()).addToBackStack(null).commit();
                btn_weight.setVisibility(View.INVISIBLE);
                btn_height.setVisibility(View.INVISIBLE);
                btn_age.setVisibility(View.INVISIBLE);
            }
        });

        btn_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home_page,new changeWeight()).addToBackStack(null).commit();
                btn_weight.setVisibility(View.INVISIBLE);
                btn_height.setVisibility(View.INVISIBLE);
                btn_age.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btn_weight.setVisibility(View.VISIBLE);
        btn_height.setVisibility(View.VISIBLE);
        btn_age.setVisibility(View.VISIBLE);

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



                agetx.setText("You are "+ age + " years old");
                heighttx.setText("You are "+ height_ft+"'' " +height_in+"' or "+ height_cm +" cm tall");
                weighttx.setText("You are "+ weight_kg+" kg or "+ weight_lb +" pounds heavy");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
