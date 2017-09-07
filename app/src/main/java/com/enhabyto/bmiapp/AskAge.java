package com.enhabyto.bmiapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskAge extends Fragment {

    private View view;
    TextView ageText;

    public AskAge() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_age, container, false);
        ageText = (TextView)view.findViewById(R.id.textView_age);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GoodDog.otf");
        ageText.setTypeface(typeface);

        return view;
    }

}
