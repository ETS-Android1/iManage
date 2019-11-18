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
import android.widget.Spinner;
import android.widget.Toast;

public class ExpenseFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_expense, container,false);



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
}
