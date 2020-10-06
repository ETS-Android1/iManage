package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nahimana.imanage.R;
import com.example.nahimana.imanage.model.ListCredits;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewHolder> {
    private Context context;
    private List<ListCredits> listCredits;

    public CreditAdapter(Context context, List<ListCredits> listCredits) {
        this.context = context;
        this.listCredits = listCredits;
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_credited_tab, viewGroup, false);
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder cvh, int index) {
        ListCredits listCredit = listCredits.get(index);
        cvh.creditAmount.setText(listCredit.getAmount());
        cvh.creditId.setText(listCredit.get_id());
        cvh.creditor.setText(listCredit.getCreditor());
        cvh.creditorPhone.setText(listCredit.getCreditorPhone());
        cvh.creditDueDate.setText(listCredit.getDueDate());
        cvh.creditPaymentDate.setText(listCredit.getPaymentDate());
        cvh.payedAmount.setText("Example: 80$/700$");

    }

    @Override
    public int getItemCount() {
        return listCredits.size();
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder {
        public TextView creditId, creditor, creditorPhone, creditAmount, creditPaymentDate, creditDueDate, payedAmount;

        public CreditViewHolder(@NonNull View itemView) {
            super(itemView);
            creditId = itemView.findViewById(R.id.creditId);
            creditor = itemView.findViewById(R.id.creditor);
            creditAmount = itemView.findViewById(R.id.creditAmount);
            creditDueDate = itemView.findViewById(R.id.creditDueDate);
            creditPaymentDate = itemView.findViewById(R.id.creditPaymentDate);
            creditorPhone = itemView.findViewById(R.id.creditorPhone);
            payedAmount = itemView.findViewById(R.id.payedAmount);


        }
    }
}
