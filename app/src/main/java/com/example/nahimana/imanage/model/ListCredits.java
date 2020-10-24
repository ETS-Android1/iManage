package com.example.nahimana.imanage.model;

public class ListCredits {
    private String _id;
    private String creditor;
    private String creditorPhone;
    private String amount;
    private String paymentDate;
    private String dueDate;
    private String payedAmount;
    private  String remainingDays;

    public ListCredits(String _id, String creditor, String creditorPhone, String amount, String paymentDate, String dueDate, String payedAmount, String remainingDays) {
        this._id = _id;
        this.creditor = creditor;
        this.creditorPhone = creditorPhone;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.payedAmount = payedAmount;
        this.remainingDays = remainingDays;
    }

    public String get_id() {
        return _id;
    }

    public String getCreditor() {
        return creditor;
    }

    public String getCreditorPhone() {
        return creditorPhone;
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

