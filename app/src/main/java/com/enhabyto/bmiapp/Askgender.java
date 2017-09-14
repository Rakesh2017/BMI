package com.enhabyto.bmiapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Askgender extends Fragment {

    ImageButton m, f;
    Button btn;
    Animation anim;

    public Askgender() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_askgender, container, false);
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
                FirebaseUser user = mAuth.getCurrentUser();


                    DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
                    d_parent.child("users").child(user.getUid()).child("flag").setValue("confirmed");


                Intent intent = new Intent(getActivity(), HomePage.class);
                startActivity(intent);
            }
        });



        return view;
    }

}
