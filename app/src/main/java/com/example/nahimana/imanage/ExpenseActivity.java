package com.example.nahimana.imanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class ExpenseActivity extends AppCompatActivity {
    public EditText etxAmount, etxDescription;
    public Spinner spCategory;
    Button btnExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setTitle("Record an Expense");
        etxAmount = findViewById(R.id.takenMoney);
        etxDescription = findViewById(R.id.description);
        spCategory = findViewById(R.id.expenseReason);
        chooseExpenseCategory();

        btnExpense = findViewById(R.id.storeExpense);
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeExpense();
                Toast.makeText(getApplicationContext(),"amount: " +"description: ",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void chooseExpenseCategory(){
        try {
            Spinner spinner = findViewById(R.id.expenseReason);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.available_expenses, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
            // super.onViewCreated(view, savedInstanceState);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void storeExpense(){
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.EXPENSE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jo.getString("message"), Toast.LENGTH_LONG).show();
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
                params.put("user_id", SharedUserData.getInstance(getApplicationContext()).getUserId());
                params.put("token", SharedUserData.getInstance(getApplicationContext()).getToken());
                return  params;
            }
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer "+SharedUserData.getInstance(getApplicationContext()).getToken());
                return headers;
            };
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }
}