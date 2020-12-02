package com.example.nahimana.imanage;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class DebitActivity extends AppCompatActivity {
    ImageView calendarIcon;
    EditText dateField, debiAmount,debtorPhone,debtor;
    Button saveDebit;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit);
        debiAmount = findViewById(R.id.debitedAmount);
        debtorPhone = findViewById(R.id.debtorPhone);
        debtor = findViewById(R.id.debtor);
        setTitle("Record a Debit");

        saveDebit = findViewById(R.id.saveDebit);

        saveDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDebit();
            }
        });
        calendarIcon = findViewById(R.id.CalenderIcon);
        dateField = findViewById(R.id.timeToReturn);

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dateField.setText("");
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        int hour= calendar.get(Calendar.HOUR);
                        int min = calendar.get(Calendar.MINUTE);
                        int sec = calendar.get(Calendar.SECOND);

                        String date = year + "-" + formatDate(monthOfYear+1)
                                + "-" + formatDate(dayOfMonth) + " "+ formatDate(hour) +":"+formatDate(min) +":"+ formatDate(sec);
                        dateField.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
    }
    public void createDebit(){
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.DEBIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jo = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jo.getString("message"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("debitor",debtor.getText().toString());
                params.put("amount",debiAmount.getText().toString());
                params.put("phone", debtorPhone.getText().toString());
                params.put("timeToPay",dateField.getText().toString());
                params.put("user_id", SharedUserData.getInstance(getApplicationContext()).getUserId());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer " + SharedUserData.getInstance(getApplicationContext()).getToken());
                return headers;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }
    public  String formatDate(int number) {
        return number <=9?"0"+number:String.valueOf(number);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}