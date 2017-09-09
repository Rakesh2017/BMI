package com.enhabyto.bmiapp;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskHeight extends Fragment {

    TextView textView1, textView2;
    private View view;
    Button button;
    ImageButton ft_to_cm, cm_to_ft;
    private int feet_num, inch_num, centimeter_num;
    private static final double inch_to_centimeter  = 2.54;

    NumberPicker feet, inch, centimeter;


    public AskHeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_ask_height, container, false);
         button = (Button)view.findViewById(R.id.set_age);
        ft_to_cm = (ImageButton)view. findViewById(R.id.ft_to_cm_btn);
        cm_to_ft = (ImageButton)view. findViewById(R.id.cm_to_ft_btn);

        feet = (NumberPicker) view.findViewById(R.id.number_picker_feet);
        inch = (NumberPicker) view.findViewById(R.id.number_picker_inches);
        centimeter = (NumberPicker) view.findViewById(R.id.number_picker_cm);

        ft_to_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touch_fun();
            }
        });


        cm_to_ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = String.valueOf(centimeter.getValue());
                centimeterToFeet(str);
            }
        });



        return view;
    }

    private void touch_fun(){
        double temp_total_feet, converted_centimeter;
        int temp;
        feet_num = feet.getValue();
        inch_num = inch.getValue();
        //centimeter_num = centimeter.getValue();

        temp_total_feet = (feet_num * 12) + inch_num;
        converted_centimeter = temp_total_feet * inch_to_centimeter;
        centimeter.setDividerColorResource(R.color.white);
        temp = (int)(converted_centimeter + 0.5d);
        centimeter.setValue(temp);
        feet.setDividerColorResource(R.color.red);
        inch.setDividerColorResource(R.color.red);
    }

    public void centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if(!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
        }
        feet.setValue(feetPart);
        feet.setDividerColorResource(R.color.white);
        inch.setValue(inchesPart);
        inch.setDividerColorResource(R.color.white);
        centimeter.setDividerColorResource(R.color.red);
    }

}
