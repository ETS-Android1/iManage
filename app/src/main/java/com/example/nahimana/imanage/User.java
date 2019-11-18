package com.example.nahimana.imanage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nahimana.imanage.helpers.Constants;
import com.example.nahimana.imanage.helpers.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class User extends AppCompatActivity {
    private EditText username, password1, matchPassword, initAmount, mPhone;
    private TextView error;
    private Button createBtn;
    RequestQueue requestQueue;
    ProgressDialog pd;

     private String userName, pass1, pass2, phone, balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        username = findViewById(R.id.username);
        password1 = findViewById(R.id.pass1);
        matchPassword = findViewById(R.id.pass2);
        initAmount = findViewById(R.id.initialAmount);
        mPhone = findViewById(R.id.mobilePhone);
        error = findViewById(R.id.errors);
        createBtn = findViewById(R.id.signUpBtn);

        requestQueue = Volley.newRequestQueue(User.this);
        pd = new ProgressDialog(User.this);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = username.getText().toString();
                pass1 = password1.getText().toString();
                pass2 = matchPassword.getText().toString();
                phone = mPhone.getText().toString();
                balance = initAmount.getText().toString();
               if(validateInputs(userName, pass1, pass2, phone) == true){
                   signUp(userName,pass1,phone,balance);
               }

            }
        });

    }

    private boolean validateInputs(String userName, String pass1, String pass2, String phone) {
        boolean isValid=true;
        if (userName.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || phone.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Please fill Required information", Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        return isValid;
      /*  if(pass1 == pass2)
        {
            Toast.makeText(getApplicationContext(),"Passwords Does not Match",Toast.LENGTH_SHORT).show();

        } else {
            matchPassword.setText("Passwords Does not Match");
        }*/

    }

    public void signUp(final String uName, final String uPassword, final String uPhone,final String uBalance) {

            pd.setMessage("Please wait ...");
            pd.show();

        StringRequest sr = new StringRequest(Request.Method.POST, Constants.ACC_CREATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            JSONObject jo = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                            if(jo.getBoolean("error") ==false){
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(User.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", uName);
                params.put("phone", uPhone);
                params.put("password", uPassword);
                params.put("balance", uBalance);

                return params;
            }
        };
         RequestHandler.getInstance(this).addToRequestQueue(sr);
    }

}