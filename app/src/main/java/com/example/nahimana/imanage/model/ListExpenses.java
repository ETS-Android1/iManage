package com.example.nahimana.imanage.model;

public class ListExpenses {
        private int _id;
        private String category;
        private String description;
        private int amount;
        private String date;

    public ListExpenses(int _id, String category, String description, int amount, String date) {
        this._id = _id;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
