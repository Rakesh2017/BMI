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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class changeWeight extends Fragment {
    private View view;
    private NumberPicker kg, lb;
    private int kg_num, lb_num;
    private Button p_blur;

    private Button btn_kg, btn_lb;
    private Animation anim;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();


    public changeWeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_weight, container, false);

        view =  inflater.inflate(R.layout.fragment_ask_weight, container, false);
        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotatetwo);
        btn_kg = (Button)view. findViewById(R.id.set_weight_kg_change);
        btn_lb = (Button)view. findViewById(R.id.set_weight_lb_change);
        p_blur = (Button)view.findViewById(R.id.pounds_blur_change);
        kg = (NumberPicker)view. findViewById(R.id.number_picker_kg_change);
        lb = (NumberPicker)view. findViewById(R.id.number_picker_lb_change);

        view.findViewById(R.id.kg_blur_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.kg_blur_change).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.ivback1_change).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.ivback2_change).setVisibility(View.VISIBLE);
                p_blur.setVisibility(View.VISIBLE);
                btn_kg.setVisibility(View.VISIBLE);
                btn_lb.setVisibility(View.INVISIBLE);



            }
        });


        p_blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.findViewById(R.id.ivback2_change).setVisibility(View.INVISIBLE);
                p_blur.setVisibility(View.INVISIBLE);
                view.findViewById(R.id.kg_blur_change).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ivback1_change).setVisibility(View.VISIBLE);
                btn_kg.setVisibility(View.INVISIBLE);
                btn_lb.setVisibility(View.VISIBLE);
            }
        });

        btn_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_parent.child("users").child(user.getUid()).child("weight").child("kilograms").setValue(kg.getValue());
                kg_num = kg.getValue();
                double temp;
                int converted_lb;
                temp = kg_num * 2.20462;
                converted_lb = (int)(temp+0.5d);
                d_parent.child("users").child(user.getUid()).child("weight").child("pounds").setValue(converted_lb);
                view.findViewById(R.id.tick_weight_change).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_weight_change).startAnimation(anim);
            }
        });

        btn_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_parent.child("users").child(user.getUid()).child("weight").child("pounds").setValue(lb.getValue());
                lb_num = lb.getValue();
                double temp;
                int converted_kg;
                temp = lb_num * 0.453592;
                converted_kg = (int)(temp+0.5d);
                d_parent.child("users").child(user.getUid()).child("weight").child("kilograms").setValue(converted_kg);
                view.findViewById(R.id.tick_weight_change).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_weight_change).startAnimation(anim);
            }
        });



        return view;
    }

}
