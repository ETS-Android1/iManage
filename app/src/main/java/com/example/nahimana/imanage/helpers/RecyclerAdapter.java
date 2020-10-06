package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nahimana.imanage.R;
import com.example.nahimana.imanage.model.ListDebits;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DebitViewHolder> {
    private List<ListDebits> listDebits;
    private Context context;

    public RecyclerAdapter(List<ListDebits> listDebits, Context context) {
        this.listDebits = listDebits;
        this.context = context;
    }

    @NonNull
    @Override
    public DebitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_debited_tab, parent, false);
        return new DebitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DebitViewHolder viewHolder, int position) {
        final ListDebits listDebit = listDebits.get(position);
        viewHolder.names.setText(listDebit.getNames());
        viewHolder.phone.setText(listDebit.getPhone());
        viewHolder.dueDate.setText(listDebit.getDueDate());
        viewHolder.paymentDate.setText(listDebit.getPaymentDate());
        viewHolder.amount.setText(listDebit.getAmount());
        viewHolder.cardView.setRadius(16);

    }

    @Override
    public int getItemCount() {
        return listDebits.size();
    }

    public class DebitViewHolder extends RecyclerView.ViewHolder {
        public TextView names, phone, dueDate, paymentDate,amount;
        public CardView cardView;

        public DebitViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.debtorNames);
            phone = itemView.findViewById(R.id.debtorPhone);
            dueDate = itemView.findViewById(R.id.dueDate);
            paymentDate = itemView.findViewById(R.id.paymentDate);
            amount = itemView.findViewById(R.id.debtorAmount);

            cardView = itemView.findViewById(R.id.debitedCV);
        }

    }
}
