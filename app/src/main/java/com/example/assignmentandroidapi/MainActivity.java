package com.example.assignmentandroidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.assignmentandroidapi.model.User;

public class MainActivity extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }
    public User getUser(){
        return user;
    }
}