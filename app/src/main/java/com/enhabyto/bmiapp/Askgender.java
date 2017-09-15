package com.enhabyto.bmiapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Askgender extends Fragment {

    ImageButton m, f;
    Button btn;
    Animation anim;

    private FirebaseAuth mAuth;

    private String gender;
    private int weight_kg, height_ft, age;




    View view;


    public Askgender() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_askgender, container, false);
        m = (ImageButton)view.findViewById(R.id.male);
        f = (ImageButton)view.findViewById(R.id.female);
        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotatetwo);

        btn = (Button)view. findViewById(R.id.enter_home);

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                    DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
                    d_parent.child("users").child(user.getUid()).child("gender").setValue("male");
                view.findViewById(R.id.male_s).setVisibility(View.VISIBLE);
                view.findViewById(R.id.female_s).setVisibility(View.INVISIBLE);



            }
        });
        f.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();


                            DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
                            d_parent.child("users").child(user.getUid()).child("gender").setValue("female");
                            view.findViewById(R.id.female_s).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.male_s).setVisibility(View.INVISIBLE);



                    }
                });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();


                if(age == 1){
                    show_message("please, provide age !");
                    return;
                }
                if(height_ft == 1){
                    show_message("please, provide height !");
                    return;
                }
                if(weight_kg == 1){
                    show_message("please, provide weight !");
                    return;
                }
             if(gender.equals("uni")){
                    show_message("please, provide your gender for accurate BMI!");
                    return;
                }

                DatabaseReference d_parent1 = FirebaseDatabase.getInstance().getReference();
                DatabaseReference d_ref1 = d_parent1.child("users");

                d_ref1.child(user.getUid()).child("flag").setValue("confirmed");

                Intent intent = new Intent(getActivity(), HomePage.class);
                startActivity(intent);



            }
        });



        return view;
    }

    public void onStart(){
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
        DatabaseReference d_ref = d_parent.child("users").child(user.getUid());
        d_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                age = dataSnapshot.child("age").getValue(Integer.class);
                weight_kg = dataSnapshot.child("weight").child("kilograms").getValue(Integer.class);
                height_ft = dataSnapshot.child("height").child("feet_and_inches").child("feet").getValue(Integer.class);
                gender = dataSnapshot.child("gender").getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Toast.makeText(getActivity(), "Database server error", Toast.LENGTH_SHORT).show();
            }
        });




    }

    protected  void show_message(String mess){

        String message;
        int color;
        message = mess;
        color = Color.RED;
        Snackbar snackbar = Snackbar.make(view.findViewById(R.id.enter_home), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(color);
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();

    }



}
