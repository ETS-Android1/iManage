package com.example.nahimana.imanage;

import android.app.DatePickerDialog;
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

public class CreditActivity extends AppCompatActivity {
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

        dateIcon = findViewById(R.id.dateIcon);
        dateText = findViewById(R.id.timeToPay);
        dateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dateText.setText("");
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DebitActivity df = new DebitActivity();

                        int hour= calendar.get(Calendar.HOUR);
                        int min = calendar.get(Calendar.MINUTE);
                        int sec = calendar.get(Calendar.SECOND);

                        String date = year + "-" + df.formatDate(monthOfYear+1)
                                + "-" + df.formatDate(dayOfMonth) + " "+ df.formatDate(hour) +":"+df.formatDate(min) +":"+ df.formatDate(sec);

                        dateText.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
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
                    Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_LONG).show();
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
}