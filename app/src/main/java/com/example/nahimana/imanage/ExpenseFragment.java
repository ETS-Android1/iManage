package com.example.nahimana.imanage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class ExpenseFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    private String selectedCategory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_expense, container,false);


       return  view;
       }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
try {


    spinner = getView().findViewById(R.id.expenseReason);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.available_expenses, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    // super.onViewCreated(view, savedInstanceState);
}
  catch (Exception e) {
    e.printStackTrace();
  }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), selectedCategory, Toast.LENGTH_SHORT).show();
        try {
            EditText detail = getView().findViewById(R.id.detail);
            EditText takMoney = getView().findViewById(R.id.takenMoney);
            Button spendBtn = getView().findViewById(R.id.saveBtn);

            final String dtl = detail.getText().toString();
            final String tMny = takMoney.getText().toString();

            spendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(),"detail: "+dtl + " money"+tMny,Toast.LENGTH_LONG).show();
                    StringRequest sr = new StringRequest(Request.Method.POST, Constants.EXPENSE_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();

                            try {
                                JSONObject jo = new JSONObject(response);
                                Toast.makeText(getActivity(),jo.getString("message"),Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", SharedUserData.getInstance(getActivity().getApplicationContext()).getUserId());
                            params.put("category", selectedCategory);
                             params.put("detail", dtl);
                             params.put("moneyTaken", tMny);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);
                }
            });

        }catch (Exception e) {
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
