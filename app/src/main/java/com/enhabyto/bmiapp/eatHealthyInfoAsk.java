package com.enhabyto.bmiapp;


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
public class eatHealthyInfoAsk extends Fragment {

    private View view;
    private TextView textView1, textView2, textView3, textView4;
    private ImageView imageView;
    private Animation anim_zoom;
    private ImageView imageView1, imageView2;
    private Animation anim;


    public eatHealthyInfoAsk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_eat_healthy_info_ask, container, false);
        textView1 = (TextView)view.findViewById(R.id.health1_text1);
        textView2 = (TextView)view.findViewById(R.id.health1_text2);
        textView3 = (TextView)view.findViewById(R.id.health1_text3);
        textView4 = (TextView)view.findViewById(R.id.health1_text4);
        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_slow);

        imageView = (ImageView)view.findViewById(R.id.apple1);
        imageView1 = (ImageView)view.findViewById(R.id.healthy_img1);
        imageView2 = (ImageView)view.findViewById(R.id.healthy_img2);
        anim_zoom = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        imageView.startAnimation(anim_zoom);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ReprineatoRegular.otf");
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);

        imageView1.startAnimation(anim);
        imageView2.startAnimation(anim);
        return view;
    }

}
