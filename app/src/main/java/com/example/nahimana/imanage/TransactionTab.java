package com.example.nahimana.imanage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nahimana.imanage.helpers.RequestHandler;
import com.example.nahimana.imanage.helpers.SharedUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static android.media.CamcorderProfile.get;
import static com.example.nahimana.imanage.helpers.Constants.TRANSACTION_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionTab extends Fragment {
    private TextView totalExpense,totalExpenseAmount, totalCredits,totalCreditsAmount, totalDebits, totalDebitsAmount, totalIncome, from;
    private SeekBar seekBar;

    public TransactionTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_tab, container, false);
        totalExpense = view.findViewById(R.id.totalExpense);
        totalCredits = view.findViewById(R.id.totalCredits);
        totalDebits = view.findViewById(R.id.totalDebits);

        totalExpenseAmount =view.findViewById(R.id.expensesAmount);
        totalDebitsAmount = view.findViewById(R.id.debitsAmount);
        totalCreditsAmount = view.findViewById(R.id.creditsAmount);
        from = view.findViewById(R.id.from);
        seekBar = view.findViewById(R.id.report);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                from.setText(getDate(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

       // totalExpenseAmount
        fetchTransactonsApi();
        return view;
    }
    private void fetchTransactonsApi(){
        StringRequest sr = new StringRequest(Request.Method.GET, TRANSACTION_URL +SharedUserData.getInstance(getContext()).getUserId(), new Response.Listener<String>() {
        // StringRequest sr = new StringRequest(Request.Method.GET, TRANSACTION_URL +"/"+15, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    if(jo.getInt("status") == 200) {
                        totalExpense.setText(jo.getString("totalExpense"));
                        totalCredits.setText(jo.getString("totalCredits"));
                        totalDebits.setText(jo.getString("totalDebits"));
                        totalDebitsAmount.setText(jo.getString("debits")+"Rwf");
                        totalCreditsAmount.setText(jo.getString("credits")+"Rwf");
                        totalExpenseAmount.setText(jo.getString("expenses")+"Rwf");
                    }
                }catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        }
        );
        RequestHandler.getInstance(getContext()).addToRequestQueue(sr);
    }

    private String getDate(int day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        String arrDate = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+1+"-"+day;
        return arrDate;

    }


}
