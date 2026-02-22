package com.example.runningapp.models;

public class ExpenseModel {
    private double amount;
    private String description;
    private String date;

    public ExpenseModel(double amount, String description, String date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
}