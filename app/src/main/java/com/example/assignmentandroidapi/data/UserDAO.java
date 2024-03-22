package com.example.assignmentandroidapi.data;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignmentandroidapi.Login;
import com.example.assignmentandroidapi.MainActivity;
import com.example.assignmentandroidapi.itf.RememberUs;
import com.example.assignmentandroidapi.model.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UserDAO {
    private String regAccount = IP.IP + "/auth/register";
    private String logAccount = IP.IP + "/auth/login";
    private String forgotPassword = IP.IP + "/auth/forgotpassword";

    private Context context;
    public UserDAO(Context context) {
        this.context = context;
    }



    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra xem chuỗi chỉ chứa đúng 10 ký tự số và bắt đầu bằng số 0
        return phoneNumber.matches("^0[0-9]{9}$");
    }
    public void setLogAccount(String email, String password,ProgressDialog progressDialog, RememberUs rememberUS){
        progressDialog.show();

        if(validateForm(email) && validateForm((password))){
            Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (!isValidEmail(email)) {
            Toast.makeText(context, "Sai định dạng email!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",email);
            jsonObject.put("password", password);
        }catch (JSONException e){
            e.printStackTrace();
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, logAccount, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    JSONObject data = response.getJSONObject("data");
                    if(message.equals("success")){
                        User user = new User(
                                data.getString("id"),
                                data.getString("fullname"),
                                data.getString("address"),
                                data.getString("email"),
                                data.getString("phone")
                        );
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("user", user);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String message = jsonObject.getString("message");
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Volley.newRequestQueue(context).add(request);
    }
    // reg account
    public void setRegAccount(String name, String email, String phone, String pw,ProgressDialog progressDialog){
        progressDialog.show();
        if (validateForm(name) && validateForm(email) && validateForm(phone)  && validateForm(pw)){
            Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(context, "Sai định dạng email!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

            return;
        }

        if (!isValidPhoneNumber(phone)) {
            Toast.makeText(context, "Số điện thoại phải có đúng 10 số và bắt đầu bằng số 0", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

            return;
        }

        // Tạo request JSON
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("fullname", name);
            jsonRequest.put("email", email);
            jsonRequest.put("password", pw);
            jsonRequest.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, regAccount, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if(message.equals("success")){
                                progressDialog.dismiss();
                                context.startActivity(new Intent(context ,  Login.class));
                                Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String message = jsonObject.getString("message");
                                progressDialog.dismiss();
                                Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        Volley.newRequestQueue(context).add(request);
    }

    public void setForgotPassword(String email, ProgressDialog progressDialog, AlertDialog alertDialog){
        progressDialog.show();
        if(validateForm(email)){
            Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if (!isValidEmail(email)) {
            Toast.makeText(context, "Sai định dạng email!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("email",email);
        }catch (JSONException e){
            e.printStackTrace();
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, forgotPassword, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    alertDialog.dismiss();
                    String message = response.getString("message");
                    Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String message = jsonObject.getString("message");
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Volley.newRequestQueue(context).add(request);
    }

    private boolean validateForm(String str){
        return (str.isEmpty() || str.equals("")) ? true : false ;

    }



}
