package com.example.nahimana.imanage.model;

public class ListExpenses {
        private String _id;
        private String category;
        private String description;
        private String amount;
        private String date;

    public ListExpenses(String _id, String category, String description, String amount, String date) {
        this._id = _id;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return _id;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
