package com.example.nahimana.imanage;


import android.content.Context;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.nahimana.imanage.helpers.Constants;
import com.example.nahimana.imanage.helpers.ExpenseAdapter;
import com.example.nahimana.imanage.helpers.RequestHandler;
import com.example.nahimana.imanage.helpers.SharedUserData;
import com.example.nahimana.imanage.model.ListDebits;
import com.example.nahimana.imanage.model.ListExpenses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpentTab extends Fragment {
    public List<ListExpenses> listExpense;
    public Context context;
    public RecyclerView.Adapter adapter;
    public RecyclerView recyclerView;

    public SpentTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        listExpense = new ArrayList<>();
        loadExpenseFromApi();

        return view;
    }
 private void loadExpenseFromApi(){

     JsonArrayRequest jar = new JsonArrayRequest(Constants.EXPENSE_URL, new Response.Listener<JSONArray>() {
         @Override
         public void onResponse(JSONArray response) {

             try {
                 if(response.length() <1 ){
                     Toast.makeText(getContext(), "You Have No Expense Yet",Toast.LENGTH_LONG).show();
                 }
                 for(int i=0; i< response.length(); i++) {
                     JSONObject jo = response.getJSONObject(i);
                     ListExpenses le = new ListExpenses(
                              String.valueOf(i),
                             jo.getString("category"),
                             jo.getString("description"),
                             jo.getString("amount"),
                             jo.getString("date")
                     );
                     listExpense.add(le);
                     adapter = new ExpenseAdapter(listExpense, getContext());
                     recyclerView.setAdapter(adapter);

                 }

             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             Log.e("Volley", error.toString());
             error.printStackTrace();
         }
     }) {

         @Override
         public Map<String, String> getHeaders(){
             HashMap<String, String> headers = new HashMap<>();
             headers.put("Authorization", "Bearer  "+SharedUserData.getInstance(context).getToken());
             return headers;

         }
     };

     RequestHandler.getInstance(getContext()).addToRequestQueue(jar);
 }
}
