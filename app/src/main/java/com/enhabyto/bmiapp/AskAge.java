package com.enhabyto.bmiapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.NumberPicker;
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
public class AskAge extends Fragment {

    private View view;
    TextView ageText;
    com.shawnlin.numberpicker.NumberPicker numberPicker;
    Button age;
    private int user_age;

    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    private String UserId = currentFirebaseUser.getUid();
    private  String pid = currentFirebaseUser.getProviderId();
    private DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();

    Animation anim;


    public AskAge() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_age, container, false);



        age = (Button)view.findViewById(R.id.set_age);

        numberPicker = (com.shawnlin.numberpicker.NumberPicker) view. findViewById(R.id.number_picker);

        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotatetwo);

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_age  = numberPicker.getValue();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                d_parent.child("users").child(user.getUid()).child("age").setValue(user_age);
                view.findViewById(R.id.tick_age).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_age).startAnimation(anim);


            }
        });




        return view;
    }

}
