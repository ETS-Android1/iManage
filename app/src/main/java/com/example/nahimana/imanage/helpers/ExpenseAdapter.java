package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nahimana.imanage.R;
import com.example.nahimana.imanage.model.ListExpenses;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<ListExpenses> listExpenses;
    private Context context;

    public ExpenseAdapter(List<ListExpenses> listExpenses, Context context) {
        this.listExpenses = listExpenses;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_spent_tab, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder evh, int position) {
        final ListExpenses listExpense = listExpenses.get(position);
        evh.expenseId.setText(listExpense.getId());
        evh.expenseCategory.setText(listExpense.getCategory());
        evh.expenseDescription.setText(listExpense.getDescription());
        evh.expenseAmount.setText(listExpense.getAmount());
        evh.expenseDate.setText(listExpense.getDate());


    }

    @Override
    public int getItemCount() {
        return listExpenses.size();
    }
    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView expenseId, expenseCategory, expenseDescription, expenseAmount, expenseDate;
        public CardView cardView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseId = itemView.findViewById(R.id.expenseId);
            expenseCategory = itemView.findViewById(R.id.expenseCategory);
            expenseDescription = itemView.findViewById(R.id.expenseDescription);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            cardView = itemView.findViewById(R.id.expenseCV);

        }
    }
}
