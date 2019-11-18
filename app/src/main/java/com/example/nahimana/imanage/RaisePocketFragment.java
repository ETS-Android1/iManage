package com.example.nahimana.imanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RaisePocketFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_raise_pocket, container,false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        try {
            Button   addIt = getView().findViewById(R.id.saveNewMoney);
            addIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText amountTxt = getView().findViewById(R.id.newMoney);
                    final String amountEtxt = (amountTxt.getText().toString());
                    if(TextUtils.isEmpty(amountEtxt)){
                        amountTxt.setError("Fund can not be empty");
                        return;
                    }
                    int amount = Integer.parseInt(amountEtxt);
                    int sum=0;

                    String  balance = SharedUserData.getInstance(getActivity().getApplicationContext()).getBalance();
                    sum = Integer.parseInt(balance) + amount;


                    //Toast.makeText(getActivity(), "new Pocket"+sum, Toast.LENGTH_SHORT).show();
                    final int finalSum = sum;
                    StringRequest sr= new StringRequest(Request.Method.POST, Constants.RAISE_POCKET_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                          Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("user_id",SharedUserData.getInstance(getActivity().getApplicationContext()).getUserId());
                            params.put("Deposited_amount",amountEtxt);
                            params.put("balance", String.valueOf(finalSum));
                            return params;
                        }
                    };
                   RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }



    }
    private void raisePocket(){

//        SharedUserData.getInstance(this).getBalance()+amount;


    }

}
