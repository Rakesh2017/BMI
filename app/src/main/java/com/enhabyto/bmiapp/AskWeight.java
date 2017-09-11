package com.enhabyto.bmiapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.shawnlin.numberpicker.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskWeight extends Fragment {

    private View view;
    ImageButton kg_to_lb, lb_to_kg;
    NumberPicker kg, lb;
    int kg_num, lb_num;


    public AskWeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ask_weight, container, false);
        kg_to_lb = (ImageButton)view. findViewById(R.id.kg_to_lb_btn);
        lb_to_kg = (ImageButton)view. findViewById(R.id.lb_to_kg_btn);

        kg = (NumberPicker)view. findViewById(R.id.number_picker_kg);
        lb = (NumberPicker)view. findViewById(R.id.number_picker_lb);

        kg_to_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kg_num = kg.getValue();
                double temp;
                int converted_lb;
                temp = kg_num * 2.20462;
                converted_lb = (int)(temp+0.5d);
                lb.setValue(converted_lb);
                lb.setDividerColorResource(R.color.white);


            }
        });

        lb_to_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lb_num = lb.getValue();
                double temp;
                int converted_kg;
                temp = lb_num * 0.453592;
                converted_kg = (int)(temp+0.5d);
                kg.setValue(converted_kg);
                kg.setDividerColorResource(R.color.white);


            }
        });
        return view;
    }

}
