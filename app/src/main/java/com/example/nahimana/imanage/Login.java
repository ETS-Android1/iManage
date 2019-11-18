package com.example.nahimana.imanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nahimana.imanage.helpers.Constants;
import com.example.nahimana.imanage.helpers.RequestHandler;
import com.example.nahimana.imanage.helpers.SharedUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
 TextView newAccount,username,password;
 Button  signBn;
 ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newAccount = findViewById(R.id.noAccount);
        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passLogin);
        signBn = findViewById(R.id.signIpBtn);

        pd = new ProgressDialog(this);
        pd.setMessage("Authenticating Please wait...");

        signBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final String userName = username.getText().toString().trim();
               final String passWord = password.getText().toString().trim();

                pd.show();

                StringRequest sr = new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == true) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            } else if(jsonObject.getBoolean("error")== false) {
                                SharedUserData.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getString("user_id"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("phone"),
                                        jsonObject.getString("balance")
                                );
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("username", userName);
                        params.put("password",passWord);

                        return params;
                    }
                };
                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(sr);
            }
        });
    }
    public void goToRegister(View v)
    {
        startActivity(new Intent(this,User.class));
    }


}
