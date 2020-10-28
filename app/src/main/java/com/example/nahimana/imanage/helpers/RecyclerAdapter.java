package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.nahimana.imanage.R;
import com.example.nahimana.imanage.model.ListDebits;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DebitViewHolder> implements Filterable {
    private List<ListDebits> listDebits;
    private List<ListDebits> debitsFullList;
    private Context context;
    public OnItemClickListener mListener;

    public RecyclerAdapter(List<ListDebits> listDebits, Context context) {
        this.listDebits = listDebits;
        this.context = context;
        debitsFullList = new ArrayList<>(listDebits);
    }

    @NonNull
    @Override
    public DebitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_debited_tab, parent, false);
        return new DebitViewHolder(v, mListener);
    }
    @Override
    public void onBindViewHolder(@NonNull DebitViewHolder viewHolder, final int position) {
        final ListDebits listDebit = listDebits.get(position);
        viewHolder.names.setText(listDebit.getNames());
        viewHolder.phone.setText(listDebit.getPhone());
        viewHolder.dueDate.setText(listDebit.getDueDate());
        viewHolder.paymentDate.setText(listDebit.getPaymentDate());
        viewHolder.amount.setText(listDebit.getAmount());
        viewHolder.payedAmount.setText("Current Payment: " + listDebit.getPayedAmount());
        viewHolder.remainingDays.setText(listDebit.getRemainingDays());
        viewHolder.cardView.setRadius(16);
        viewHolder.cardView.setClickable(true);
    }
    @Override
    public int getItemCount() {
        return listDebits.size();
    }
    public void  setOnclickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public static class DebitViewHolder extends RecyclerView.ViewHolder {
        public TextView debitId, names, phone, dueDate, paymentDate,amount,payedAmount, remainingDays;
        public EditText payDebitAmount;
        public CardView cardView;
        public View container;
        Button payDebitBtn;

        public DebitViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            debitId = itemView.findViewById(R.id.payDebitId);
            names = itemView.findViewById(R.id.debtorNames);
            phone = itemView.findViewById(R.id.debtorPhone);
            dueDate = itemView.findViewById(R.id.dueDate);
            paymentDate = itemView.findViewById(R.id.paymentDate);
            amount = itemView.findViewById(R.id.debtorAmount);
            payedAmount = itemView.findViewById(R.id.labelPaid);
            remainingDays = itemView.findViewById(R.id.remainingDayz);
            cardView = itemView.findViewById(R.id.debitedCV);
            payDebitAmount = itemView.findViewById(R.id.payDebitAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ListDebits> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(debitsFullList);
            } else  {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for(ListDebits debit: debitsFullList) {
                    if(debit.getNames().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(debit);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
         listDebits.clear();
         listDebits.addAll((List) results.values);
         notifyDataSetChanged();
        }
    };
}
