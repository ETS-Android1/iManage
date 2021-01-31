package com.example.nahimana.imanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.nahimana.imanage.helpers.SharedUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DepositActivity extends AppCompatActivity {
    Button addIt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        addIt  = findViewById(R.id.saveNewMoney);
        setTitle("Add Deposit");
        addDeposit();
        
    }
    public void addDeposit(){

        try {

            addIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText amountTxt = findViewById(R.id.newMoney);
                  final  EditText currencyTxt = findViewById(R.id.currency);
                    final String amountEtxt = amountTxt.getText().toString();
                    if(TextUtils.isEmpty(amountEtxt)){
                        amountTxt.setError("Fund can not be empty");
                        return;
                    }

                    StringRequest sr= new StringRequest(Request.Method.POST, Constants.DEPOSIT_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jo = new JSONObject(response);
                               // startActivity(new Intent(DepositActivity.this, MainActivity.class));
                                success(jo.getString("message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams()  {
                            Map<String,String> params = new HashMap<>();
                            params.put("amount", amountEtxt);
                            params.put("currency", currencyTxt.getText().toString());
                            params.put("user_id", SharedUserData.getInstance(getApplicationContext()).getUserId());

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap();
                            headers.put("Authorization", "Bearer "+SharedUserData.getInstance(getApplicationContext()).getToken());
                            return headers;
                        }
                    };
                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(sr);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void success(String message){
        SweetAlertDialog sweet = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        sweet.setContentText(message);
        sweet.setTitle("Congratulation");
        sweet.show();
    }
}