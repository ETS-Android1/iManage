package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Payment {
    private Context context;

    public Payment(final Context context,final String amount, final String id, final String paymentType) {
        this.context = context;
        String url;
        url = paymentType.equals("credit_id") ? Constants.PAY_CREDIT_URL : Constants.PAY_DEBIT_URL;
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    Toast.makeText(context, jo.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer "+ SharedUserData.getInstance(context).getToken());
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("amount", amount);
                params.put(paymentType, id);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(sr);
    }
}
