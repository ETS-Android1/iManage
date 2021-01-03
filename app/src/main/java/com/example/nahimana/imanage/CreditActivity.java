package com.example.nahimana.imanage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreditActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{
    ImageView dateIcon;
    EditText dateText, amountText, creditor, creditorPhone, timeToPay;
    Button saveCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        amountText = findViewById(R.id.creditedAmount);
        creditor = findViewById(R.id.creditor);
        creditorPhone = findViewById(R.id.creditorPhone);
        timeToPay = findViewById(R.id.timeToPay);
        saveCredit = findViewById(R.id.saveCredit);
        setTitle("Record A Credit");


        dateText = findViewById(R.id.timeToPay);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dateText.setText("");
                DialogFragment df = new DatePickerFragment();
                df.show(getSupportFragmentManager(),"Date Picker");
                
            }
        });
        
        saveCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCredit();
            }
        });
    }
    private void createCredit(){
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.CREDIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    success(jo.getString("message"));
                    //startActivity(new Intent(CreditActivity.this, MainActivity.class));

                }catch (JSONException je){
                    je.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("creditor", creditor.getText().toString());
                params.put("amount", amountText.getText().toString());
                params.put("phone", creditorPhone.getText().toString());
                params.put("timeToPay", timeToPay.getText().toString());
                params.put("user_id", SharedUserData.getInstance(getApplicationContext()).getUserId());
                return params;
            };
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer "+SharedUserData.getInstance(getApplicationContext()).getToken());
                return headers;
            };
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        DebitActivity da = new DebitActivity();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        int hour, min, sec;
        hour = c.get(Calendar.HOUR);
        min = c.get(Calendar.MINUTE);
        sec = c.get(Calendar.SECOND);

        String date = year + "-" + da.formatDate(month+1)
                + "-" + da.formatDate(dayOfMonth) + " "+ da.formatDate(hour) +":"+da.formatDate(min) +":"+ da.formatDate(sec);

        dateText.setText(date);
    }

    public void success(String message){
        SweetAlertDialog sweet = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        sweet.setContentText(message);
        sweet.setTitle("Congratulation");
        sweet.show();
    }
}