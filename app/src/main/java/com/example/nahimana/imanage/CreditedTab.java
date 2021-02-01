package com.example.nahimana.imanage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.nahimana.imanage.helpers.Constants;
import com.example.nahimana.imanage.helpers.CreditAdapter;
import com.example.nahimana.imanage.helpers.Payment;
import com.example.nahimana.imanage.helpers.PaymentAlertDialog;
import com.example.nahimana.imanage.helpers.RecyclerAdapter;
import com.example.nahimana.imanage.helpers.RequestHandler;
import com.example.nahimana.imanage.helpers.SharedUserData;
import com.example.nahimana.imanage.model.ListCredits;

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
public class CreditedTab extends Fragment implements CreditAdapter.OnCreditClickListener {
    List<ListCredits> listCredits;
    RecyclerView recyclerView;
    Context context;
    CreditAdapter creditAdapter;
     CreditAdapter.OnCreditClickListener creditClickListener;

    public CreditedTab() {
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
            listCredits = new ArrayList<>();
            loadCreditsFromApi();
            creditClickListener = this;
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void loadCreditsFromApi() {
        JsonArrayRequest credits = new JsonArrayRequest(Constants.CREDIT_URL , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    try {
                        for(int i=0; i<response.length(); i++) {
                            JSONObject jo = response.getJSONObject(i);
                            ListCredits lc = new ListCredits(
                                jo.getString("id"),
                                jo.getString("creditor"),
                                jo.getString("phone"),
                                jo.getString("amount"),
                                jo.getString("timeToPay"),
                                jo.getString("date"),
                                jo.getString("payedAmount"),
                                jo.getString("remainingDays")
                            );
                            listCredits.add(lc);
                            creditAdapter = new CreditAdapter(context, listCredits, creditClickListener);
                            recyclerView.setAdapter(creditAdapter);
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
        }) {
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap();
                headers.put("Authorization", "Bearer "+ SharedUserData.getInstance(getContext()).getToken());
                return headers;
            };
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(credits);

    }
    @Override
    public void onCreditClick(ListCredits lc) {
        PaymentAlertDialog.showDialog(getContext(),"credit_id",lc.get_id());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        MenuItem mi = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) mi.getActionView();
        sv.setImeOptions(EditorInfo.IME_FLAG_FORCE_ASCII);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                creditAdapter.getFilter().filter(newText);
                 return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
