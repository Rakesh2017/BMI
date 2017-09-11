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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskAge extends Fragment {

    private View view;
    TextView ageText;
    com.shawnlin.numberpicker.NumberPicker numberPicker;
    Button age;
    private int user_age;

    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    private String UserId = currentFirebaseUser.getUid();
    private  String pid = currentFirebaseUser.getProviderId();
    private DatabaseReference mDatabase;

    public AskAge() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_age, container, false);



        age = (Button)view.findViewById(R.id.set_age);

        numberPicker = (com.shawnlin.numberpicker.NumberPicker) view. findViewById(R.id.number_picker);


        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_age  = numberPicker.getValue();
                Toast.makeText(getActivity(), "You are "+ user_age+ " old", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), UserId  + pid, Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }

}
