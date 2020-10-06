package com.example.nahimana.imanage.model;

public class ListDebits {
    private String Names;
    private String phone;
    private String amount;
    private String paymentDate;

    public String getNames() {
        return Names;
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

    private String dueDate;

    public ListDebits(String names, String phone, String amount, String paymentDate, String dueDate) {
        Names = names;
        this.phone = phone;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
    }

    }
