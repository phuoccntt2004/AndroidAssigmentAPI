package com.example.assignmentandroidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignmentandroidapi.data.UserDAO;

public class Register extends AppCompatActivity {
    private Button btnSignUp;
    private TextView txtLogin;
    private UserDAO userDAO;
    private ProgressDialog progressDialog;
    private EditText edphone, edname,  edpw, edemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);
        edname = findViewById(R.id.regedtHoTen);
        edphone = findViewById(R.id.regphone);
        edemail = findViewById(R.id.regemail);
        edpw = findViewById(R.id.regedtAddPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString().trim();
                String email = edemail.getText().toString().trim();
                String phone = edphone.getText().toString().trim();
                String pw= edpw.getText().toString().trim();

                userDAO = new UserDAO(Register.this);
                userDAO.setRegAccount(name, email, phone,pw,progressDialog);
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }


}