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
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.nahimana.imanage.helpers.Constants;
import com.example.nahimana.imanage.helpers.Payment;
import com.example.nahimana.imanage.helpers.RecyclerAdapter;
import com.example.nahimana.imanage.helpers.RequestHandler;
import com.example.nahimana.imanage.helpers.SharedUserData;
import com.example.nahimana.imanage.model.ListDebits;

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
public class DebitedTab extends Fragment implements RecyclerAdapter.OnItemClickListener{

        private RecyclerView recyclerView;
        private RecyclerAdapter adapter;

        private List<ListDebits> listDebits;
        private ArrayList mDebits;
        private Context context;
        private RecyclerAdapter.OnItemClickListener listener;
        private Button payDebitBtn;
        EditText amount;

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
             listener = this;
             return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    public void loadDebitsFromApi(){
        JsonArrayRequest jar = new JsonArrayRequest(Constants.DEBIT_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i< response.length(); i++) {

                        JSONObject jo = response.getJSONObject(i);
                        ListDebits ld = new ListDebits(
                             jo.getString("id"),
                            jo.getString("names"),
                            jo.getString("phone"),
                            jo.getString("amount"),
                            jo.getString("timeToPay"),
                            jo.getString("date"),
                            jo.getString("payedAmount"),
                            jo.getString("remainingDays")
                        );
                        listDebits.add(ld);
                        adapter = new RecyclerAdapter(listDebits, context, listener);
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
        }){
                @Override
                public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer  " + SharedUserData.getInstance(context).getToken());
                return headers;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(jar);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_FLAG_FORCE_ASCII);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
       super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDebitClick(ListDebits liDebits, String amount) {
       new Payment(getContext(), amount, liDebits.getId(), "debit_id");
    }
}

