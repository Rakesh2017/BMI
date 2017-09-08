package com.enhabyto.bmiapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskAge extends Fragment {

    private View view;
    TextView ageText;
    com.shawnlin.numberpicker.NumberPicker numberPicker;
    Button age;
    private int user_age;

    public AskAge() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_age, container, false);
        ageText = (TextView)view.findViewById(R.id.id_age_Text);


        age = (Button)view.findViewById(R.id.set_age);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/sportsfont.ttf");
        ageText.setTypeface(typeface);


        numberPicker = (com.shawnlin.numberpicker.NumberPicker) view. findViewById(R.id.number_picker);


        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_age  = numberPicker.getValue();
                Toast.makeText(getActivity(), "You are "+ user_age+ " old", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
