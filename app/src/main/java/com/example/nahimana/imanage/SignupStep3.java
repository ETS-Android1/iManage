package com.example.nahimana.imanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nahimana.imanage.helpers.Constants;
import com.example.nahimana.imanage.helpers.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignupStep3 extends AppCompatActivity {
    Button btnSignup;
    EditText balance,phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step3);
        btnSignup= findViewById(R.id.signUpBtn);
        balance = findViewById(R.id.userBalance);
        phoneNumber = findViewById(R.id.userPhone);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call api
                try{
                    Intent intent = getIntent();

                    signUp( intent.getStringExtra("email"),
                            intent.getStringExtra("names"),
                            intent.getStringExtra("password"),
                            intent.getStringExtra("password_confirmation"),
                            phoneNumber.getText().toString(),
                            balance.getText().toString());

                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void signUp(final String uEmail, final String uName, final String uPassword, final String pass2,  final String uPhone,final String uBalance) {

        StringRequest sr = new StringRequest(Request.Method.POST, Constants.USER_REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jo = new JSONObject(response);
                            if(jo.getBoolean("error")){
                                responseDialog(jo.getString("message").replaceAll("['\"',',']"," \n"), "Oops","warning");

                            } else {
                               startActivity(new Intent(getApplicationContext(), Login.class));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseDialog("There is a problem with connection", "Oops","error");

                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", uName);
                params.put("email", uEmail);
                params.put("phone", uPhone);
                params.put("password", uPassword);
                params.put("password_confirmation", pass2);
                params.put("balance", uBalance);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(sr);

    }
    private void responseDialog(String message, String title, String type) {
        SweetAlertDialog sw = null;
        switch (type) {
            case "success":
                sw = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
                break;
            case "warning":
                sw = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                break;
            case "error":
                sw = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
                break;
            default:
        }
       sw.setTitleText(title)
                .setContentText(message)
                .show();

    }

}