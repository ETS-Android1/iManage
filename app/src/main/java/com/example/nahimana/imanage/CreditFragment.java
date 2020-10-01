package com.example.nahimana.imanage;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreditFragment extends Fragment {
ImageView dateIcon;
EditText dateText, amountText, creditor, creditorPhone, timeToPay;
Button saveCredit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_credit, container, false);
        amountText = view.findViewById(R.id.creditedAmount);
        creditor = view.findViewById(R.id.creditor);
        creditorPhone = view.findViewById(R.id.creditorPhone);
        timeToPay = view.findViewById(R.id.timeToPay);
        saveCredit = view.findViewById(R.id.saveCredit);
        saveCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCredit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dateIcon = getView().findViewById(R.id.dateIcon);
        dateText = getView().findViewById(R.id.timeToPay);
        dateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dateText.setText("");
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DebitFragment df = new DebitFragment();

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

    }
    private void createCredit(){
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.CREDIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    Toast.makeText(getContext(), jo.getString("message"), Toast.LENGTH_LONG).show();
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
                params.put("user_id", SharedUserData.getInstance(getContext()).getUserId());
                params.put("token", SharedUserData.getInstance(getContext()).getToken());
                return params;
            };
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(sr);
    }
}

