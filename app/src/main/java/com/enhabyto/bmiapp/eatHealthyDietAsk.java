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
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class eatHealthyDietAsk extends Fragment {

    private View view;
    private TextView textView1, textView2;
    private Button button1, button2, button3, button4, button5, button6;
    ImageView imageView;
    Animation anim_zoom;

    public eatHealthyDietAsk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_eat_healthy_diet_ask, container, false);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ReprineatoRegular.otf");
        textView1 = (TextView)view.findViewById(R.id.health_text1);
        textView2 = (TextView)view.findViewById(R.id.health_text2);
        button1  = (Button)view. findViewById(R.id.health_btn1);
        button2  = (Button)view. findViewById(R.id.health_btn2);
        button3  = (Button)view. findViewById(R.id.health_btn3);
        button4  = (Button)view. findViewById(R.id.health_btn4);
        button5  = (Button)view. findViewById(R.id.health_btn5);
        button6  = (Button)view. findViewById(R.id.health_btn6);
        imageView = (ImageView)view.findViewById(R.id.apple);
        anim_zoom = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        imageView.startAnimation(anim_zoom);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAll();
               button1.setBackgroundResource(R.drawable.rounded_background_green);
                button1.setTextColor(Color.BLACK);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAll();
                button2.setBackgroundResource(R.drawable.rounded_background_green);
                button2.setTextColor(Color.BLACK);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAll();
                button3.setBackgroundResource(R.drawable.rounded_background_green);
                button3.setTextColor(Color.BLACK);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAll();
                button4.setBackgroundResource(R.drawable.rounded_background_green);
                button4.setTextColor(Color.BLACK);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAll();
                button5.setBackgroundResource(R.drawable.rounded_background_green);
                button5.setTextColor(Color.BLACK);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAll();
                button6.setBackgroundResource(R.drawable.rounded_background_green);
                button6.setTextColor(Color.BLACK);
            }
        });
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        return view;
    }

    private void changeAll(){
        button1.setBackgroundResource(R.drawable.rounded_background_blue);
        button1.setTextColor(Color.WHITE);
        button2.setBackgroundResource(R.drawable.rounded_background_blue);
        button2.setTextColor(Color.WHITE);
        button3.setBackgroundResource(R.drawable.rounded_background_blue);
        button3.setTextColor(Color.WHITE);
        button4.setBackgroundResource(R.drawable.rounded_background_blue);
        button4.setTextColor(Color.WHITE);
        button5.setBackgroundResource(R.drawable.rounded_background_blue);
        button5.setTextColor(Color.WHITE);
        button6.setBackgroundResource(R.drawable.rounded_background_blue);
        button6.setTextColor(Color.WHITE);

    }
}
