package com.example.assignmentandroidapi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.assignmentandroidapi.Login;
import com.example.assignmentandroidapi.R;

public class OnBoarDingFragment2 extends Fragment {
    private Button btnBatDau;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding2, container, false);

        btnBatDau = view.findViewById(R.id.bntBatDau);

        btnBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        return view;
    }
}