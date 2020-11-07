package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nahimana.imanage.R;
import com.example.nahimana.imanage.model.ListCredits;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewHolder> {
    private Context context;
    private List<ListCredits> listCredits;
     private OnCreditClickListener ccl;

    public CreditAdapter(Context context, List<ListCredits> listCredits,OnCreditClickListener ccl ) {
        this.context = context;
        this.listCredits = listCredits;
        this.ccl = ccl;
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_credited_tab, viewGroup, false);
        return new CreditViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final CreditViewHolder cvh, int index) {
        final ListCredits listCredit = listCredits.get(index);
        cvh.creditAmount.setText(listCredit.getAmount());
        cvh.creditId.setText(listCredit.get_id());
        cvh.creditorName.setText(listCredit.getCreditor());
        cvh.creditorPhone.setText(listCredit.getCreditorPhone());
        cvh.creditDueDate.setText(listCredit.getDueDate());
        cvh.creditPaymentDate.setText(listCredit.getPaymentDate());
        cvh.payedAmount.setText("CurrentPayment: "+listCredit.getPayedAmount());
        cvh.remainingDays.setText(listCredit.getRemainingDays());
        cvh.payCreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ccl.onCreditClick(listCredit, cvh.payCreditAmount.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCredits.size();
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder {
        public TextView creditId, creditorName, creditorPhone, creditAmount, creditPaymentDate, creditDueDate, payedAmount, remainingDays, payCreditAmount;
        public Button payCreditButton;

        public CreditViewHolder(@NonNull View itemView) {
            super(itemView);
            creditId = itemView.findViewById(R.id.creditId);
            creditorName = itemView.findViewById(R.id.creditorName);
            creditAmount = itemView.findViewById(R.id.creditAmount);
            creditDueDate = itemView.findViewById(R.id.creditDueDate);
            creditPaymentDate = itemView.findViewById(R.id.creditPaymentDate);
            creditorPhone = itemView.findViewById(R.id.creditorPhone);
            payedAmount = itemView.findViewById(R.id.payedAmount);
            remainingDays = itemView.findViewById(R.id.remainingDays);
            payCreditAmount = itemView.findViewById(R.id.payCreditAmount);
            payCreditButton = itemView.findViewById(R.id.payCreditBtn);
        }
    }
    public interface OnCreditClickListener {
        void onCreditClick(ListCredits lc, String amount);
    }
}
