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

import org.json.JSONException;
import org.json.JSONObject;

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
                                Toast.makeText(getContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();

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
                            params.put("user_id", SharedUserData.getInstance(getContext()).getUserId());

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap();
                            headers.put("Authorization", "Bearer "+SharedUserData.getInstance(getContext()).getToken());
                            return headers;
                        }
                    };

                   RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }



    }


}
