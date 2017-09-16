package com.enhabyto.bmiapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class changeHeight extends Fragment {

    private TextView textView1, textView2;
    private View view;
    private Button button, f_blur, c_blur, btn_f, btn_c;
    private ImageButton ft_to_cm, cm_to_ft;
    private int feet_num, inch_num, centimeter_num;
    private static final double inch_to_centimeter  = 2.54;

    private NumberPicker feet, inch, centimeter;
    private Button btn;
    private Animation anim;

    private RelativeLayout r1, r2;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private DatabaseReference d_parent = FirebaseDatabase.getInstance().getReference();

    public changeHeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_height, container, false);

        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotatetwo);
        btn_f = (Button) view.findViewById(R.id.set_height_feet_change);
        btn_c = (Button) view.findViewById(R.id.set_height_cm_change);
        r1 = (RelativeLayout)view.findViewById(R.id.rl1_change);
        r2 = (RelativeLayout)view.findViewById(R.id.rl2_change);
        f_blur = (Button)view. findViewById(R.id.feet_blur_change);
        c_blur = (Button)view. findViewById(R.id.cm_blur_change);

        feet = (NumberPicker) view.findViewById(R.id.number_picker_feet_change);
        inch = (NumberPicker) view.findViewById(R.id.number_picker_inches_change);
        centimeter = (NumberPicker) view.findViewById(R.id.number_picker_cm_change);
        //  btn = (Button)view. findViewById(R.id.set_height);


        f_blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2.setVisibility(View.VISIBLE);
                r1.setVisibility(View.INVISIBLE);
                btn_f.setVisibility(View.VISIBLE);
                btn_c.setVisibility(View.INVISIBLE);


            }
        });

        c_blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.INVISIBLE);
                btn_c.setVisibility(View.VISIBLE);
                btn_f.setVisibility(View.INVISIBLE);

            }
        });

        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("feet").setValue(feet.getValue());
                d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("inches").setValue(inch.getValue());


                double temp_total_feet, converted_centimeter;
                int temp;
                feet_num = feet.getValue();
                inch_num = inch.getValue();
                temp_total_feet = (feet_num * 12) + inch_num;
                converted_centimeter = temp_total_feet * inch_to_centimeter;
                temp = (int)(converted_centimeter + 0.5d);
                d_parent.child("users").child(user.getUid()).child("height").child("centimeter").setValue(temp);
                view.findViewById(R.id.tick_height_change).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_height_change).startAnimation(anim);


            }
        });

        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_parent.child("users").child(user.getUid()).child("height").child("centimeter").setValue(centimeter.getValue());
                String centemeter = String.valueOf(centimeter.getValue());
                int feetPart = 0;
                int inchesPart = 0;

                if(!TextUtils.isEmpty(centemeter)) {
                    double dCentimeter = Double.valueOf(centemeter);
                    feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
                    inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
                }
                d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("feet").setValue(feetPart);
                d_parent.child("users").child(user.getUid()).child("height").child("feet_and_inches").child("inches").setValue(inchesPart);
                view.findViewById(R.id.tick_height_change).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tick_height_change).startAnimation(anim);
            }
        });




        return view;
    }

}
