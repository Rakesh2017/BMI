package com.enhabyto.bmiapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class changeAge extends Fragment {

    private com.shawnlin.numberpicker.NumberPicker numberPicker;
    private Button btn;
    private ImageView imageView;
    private DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();
    private Animation anim;
    private View view;
    private int user_age;



    public changeAge() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_age, container, false);

        btn = (Button)view.findViewById(R.id.change_age_frag_btn);

        numberPicker = (com.shawnlin.numberpicker.NumberPicker) view. findViewById(R.id.number_picker_age_change);

        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotatetwo);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_age  = numberPicker.getValue();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                d_parent.child("users").child(user.getUid()).child("age").setValue(user_age);
                view.findViewById(R.id.tick_change_age).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_change_age).startAnimation(anim);


            }
        });


        return view;
    }

}
