package com.example.nahimana.imanage.model;

import android.content.Context;
import android.widget.Toast;

public class ListDebits {
    private String names;
    private String phone;
    private String amount;
    private String paymentDate;
    private String dueDate;
    private String payedAmount;
    private  String remainingDays;

    public ListDebits(String names, String phone, String amount, String paymentDate, String dueDate, String payedAmount, String remainingDays) {
        this.names = names;
        this.phone = phone;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.payedAmount = payedAmount;
        this.remainingDays = remainingDays;
    }
    public String getNames() {
        return names;
    }

    public String getPhone() {
        return phone;
    }

    public String getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPayedAmount() {
        return payedAmount;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

}
