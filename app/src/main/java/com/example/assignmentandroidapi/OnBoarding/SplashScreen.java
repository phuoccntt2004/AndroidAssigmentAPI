package com.example.assignmentandroidapi.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.assignmentandroidapi.Login;
import com.example.assignmentandroidapi.MySharedPreferences;
import com.example.assignmentandroidapi.R;


public class SplashScreen extends AppCompatActivity {
    // hằng số dùng để định danh một khóa trong SharedPreferences
    private static final String KEY_FIRST_INSTALL = "KEY_FIRST_INSTALL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        MySharedPreferences mySharedPreferences = new MySharedPreferences(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mySharedPreferences.getBooleanValue(KEY_FIRST_INSTALL)) {
                    startActivity(Login.class);
                } else {
                    startActivity(OnBoarDingActivity.class);
                    // cập nhật dữ liệu trong SharedPreferences
                    mySharedPreferences.putBooleanValue(KEY_FIRST_INSTALL, true);
                }
            }
        }, 3000);
    }

    private void startActivity(Class<?> cls) { // Phương thức được sử dụng để chuyển đổi giữa các màn hình
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }
}