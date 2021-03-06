package com.enhabyto.bmiapp;


import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskWeight extends Fragment {

    private View view;
    private ImageButton kg_to_lb, lb_to_kg;
    private NumberPicker kg, lb;
    private int kg_num, lb_num;
    private Button p_blur;
    TextView textView1;
    Animation anim_zoom;
    ImageView imageView;

    private Button btn_kg, btn_lb;
    private Animation anim;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();

    public AskWeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ask_weight, container, false);
        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotatetwo);
        btn_kg = (Button)view. findViewById(R.id.set_weight_kg);
        btn_lb = (Button)view. findViewById(R.id.set_weight_lb);
        p_blur = (Button)view.findViewById(R.id.pounds_blur);
        kg = (NumberPicker)view. findViewById(R.id.number_picker_kg);
        lb = (NumberPicker)view. findViewById(R.id.number_picker_lb);
        textView1 = (TextView)view. findViewById(R.id.weight_text1);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ReprineatoRegular.otf");
        textView1.setTypeface(typeface);
        imageView = (ImageView)view.findViewById(R.id.apple4);
        anim_zoom = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        imageView.startAnimation(anim_zoom);

        view.findViewById(R.id.kg_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.kg_blur).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.ivback1).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.ivback2).setVisibility(View.VISIBLE);
                p_blur.setVisibility(View.VISIBLE);
                btn_kg.setVisibility(View.VISIBLE);
                btn_lb.setVisibility(View.INVISIBLE);



            }
        });


        p_blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.findViewById(R.id.ivback2).setVisibility(View.INVISIBLE);
                p_blur.setVisibility(View.INVISIBLE);
                view.findViewById(R.id.kg_blur).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ivback1).setVisibility(View.VISIBLE);
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
                view.findViewById(R.id.tick_weight).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_weight).startAnimation(anim);
                btn_kg.setBackgroundResource(R.drawable.rounded_background_green);
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
                view.findViewById(R.id.tick_weight).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_weight).startAnimation(anim);
                btn_lb.setBackgroundResource(R.drawable.rounded_background_green);

            }
        });


        return view;

    }
}
