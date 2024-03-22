package com.example.assignmentandroidapi;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "loginCheck";
    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public void putBooleanValue(String key, boolean value) {
        //Phương thức này dùng để lưu giá trị boolean vào SharedPreferences dựa trên một khóa (key)
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        //  Phương thức này dùng để lấy giá trị boolean từ SharedPreferences dựa trên khóa (key) đã chỉ định
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, 0);
        return sharedPreferences.getBoolean(key, false);
    }
}
