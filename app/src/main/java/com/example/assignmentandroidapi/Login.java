package com.example.assignmentandroidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmentandroidapi.data.UserDAO;
import com.example.assignmentandroidapi.itf.RememberUs;


public class Login extends AppCompatActivity {
    private Button btnLogin;
    private TextView txtForgotPass, txtSignUp;
    private EditText edEmail, edpw;
    private CheckBox chk;
    UserDAO userDAO;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPass = findViewById(R.id.txtForgotPass);
        txtSignUp = findViewById(R.id.txtSignUp);
        chk = findViewById(R.id.chkMiss);
        edEmail = findViewById(R.id.edtEmail);
        edpw = findViewById(R.id.edtPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        checkUser();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDAO = new UserDAO(getApplicationContext());
                String email = edEmail.getText().toString().trim();
                String pw = edpw.getText().toString().trim();
                checkLogin(email, pw);
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForgot();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void showDialogForgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.forgot_password, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        EditText edtSendEmail = view.findViewById(R.id.edtSendEmail);
        Button btnSendEmail = view.findViewById(R.id.btnSendEmail);
        Button btnBackToLogin = view.findViewById(R.id.btnBackToLogin);
        btnSendEmail.setOnClickListener(v->{
            String email = edtSendEmail.getText().toString().trim();
            userDAO = new UserDAO(getApplicationContext());
            userDAO.setForgotPassword(email,progressDialog,alertDialog);
        });
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                alertDialog.dismiss();
            }
        });
    }
    boolean doubleBack = false;

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
            return;
        }

        this.doubleBack = true;
        Toast.makeText(this, "Bấm thêm 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBack=false;
            }
        }, 2000);
    }
    private void checkLogin(String us, String pw){
        if (us.isEmpty() || pw.isEmpty() || us.equals("") || us.equals(""))
            Toast.makeText(this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        else{
            userDAO.setLogAccount(us, pw,progressDialog, new RememberUs() {
                @Override
                public void remember() {
                    if (chk.isChecked()){
                        rememberus(us,pw, chk.isChecked());
                    }
                }
            });

        }

    }
    private void checkUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("check", false)){
            edEmail.setText(sharedPreferences.getString("us",""));
            edpw.setText(sharedPreferences.getString("pw", ""));
            chk.setChecked(true);
        }
    }
    public void rememberus(String us, String pw, boolean check){
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("us", us);
        editor.putString("pw", pw);
        editor.putBoolean("check",check);
        editor.commit();
    }
}