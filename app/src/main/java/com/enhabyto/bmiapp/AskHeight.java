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
public class AskHeight extends Fragment {

    TextView textView1, textView2;
    private View view;


    public AskHeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_ask_height, container, false);
         textView1 = (TextView)view.findViewById(R.id.textView_you_are);
         textView2 = (TextView)view.findViewById(R.id.textView_tall);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/sportsfont.ttf");
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);

         return view;
    }

}
