package com.example.nahimana.imanage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nahimana.imanage.helpers.Constants;
import com.example.nahimana.imanage.helpers.RecyclerAdapter;
import com.example.nahimana.imanage.helpers.RequestHandler;
import com.example.nahimana.imanage.model.ListDebits;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebitedTab extends Fragment {

        private RecyclerView recyclerView;
        private RecyclerView.Adapter adapter;
        private List<ListDebits> listDebits;
        private Context context;

    public DebitedTab() {
        // Required empty public constructor

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
             recyclerView = view.findViewById(R.id.recyclerView);
             recyclerView.setHasFixedSize(true);
             recyclerView.setLayoutManager(new LinearLayoutManager(context));

             listDebits = new ArrayList<>();
             loadDebitsFromApi();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void loadDebitsFromApi(){

        StringRequest sr = new StringRequest(Request.Method.GET, Constants.DEBIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray debits = jsonObject.getJSONArray("debits");
                    Toast.makeText(getContext(), "" + debits.length(), Toast.LENGTH_LONG).show();

                    for (int i=0; i< debits.length(); i++) {

                        JSONObject jo = debits.getJSONObject(i);
                        ListDebits ld = new ListDebits(
                            jo.getString("debitor"),
                            jo.getString("phone"),
                            jo.getString("amount"),
                            jo.getString("timeToPay"),
                            jo.getString("created_at"));

                        listDebits.add(ld);
                        adapter = new RecyclerAdapter(listDebits, context);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestHandler.getInstance(getContext()).addToRequestQueue(sr);
    }

}
