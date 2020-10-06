package com.example.nahimana.imanage;

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

     public EditText etxAmount, etxDescription;
     public Spinner spCategory;
     Button btnExpense;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense, container,false);

            etxAmount = view.findViewById(R.id.takenMoney);
            etxDescription = view.findViewById(R.id.description);
            spCategory = view.findViewById(R.id.expenseReason);

            btnExpense = view.findViewById(R.id.storeExpense);
            btnExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeExpense();
                    Toast.makeText(getContext(),"amount: " +"description: ",Toast.LENGTH_LONG).show();
                }
            });

        return  view;
       }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            Spinner spinner = getView().findViewById(R.id.expenseReason);
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
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void storeExpense()
    {
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.EXPENSE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    Toast.makeText(getContext(),jo.getString("message"), Toast.LENGTH_LONG).show();
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
          protected Map<String, String> getParams(){
              Map<String, String> params = new HashMap<>();
              params.put("category", spCategory.getSelectedItem().toString());
              params.put("description", etxDescription.getText().toString());
              params.put("amount", etxAmount.getText().toString());
              params.put("user_id", SharedUserData.getInstance(getContext()).getUserId());
              params.put("token", SharedUserData.getInstance(getContext()).getToken());
              return  params;
          }
            @Override
            public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap();
                    headers.put("Authorization", "Bearer "+SharedUserData.getInstance(getContext()).getToken());
                    return headers;
            };
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(sr);
    }
}
