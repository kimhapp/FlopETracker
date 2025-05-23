package com.flopetracker.model;

import android.net.Uri;

import com.flopetracker.util.ISO8601DateAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Expense {
    @SerializedName("id")
    private String id;

    @SerializedName("remark")
    private String remark;

    @SerializedName("amount")
    private double amount;

    @SerializedName("currency")
    private String currency;

    @SerializedName("category")
    private String category;

    @SerializedName("createdBy")
    private String user;

    @SerializedName("createdDate")
    @JsonAdapter(ISO8601DateAdapter.class)
    private Date date;

    @SerializedName("receiptImageUrl")
    private String imageUrl;

    public Expense(double amount, String currency, String category, String remark, String imageUrl) {
        this.id = UUID.randomUUID().toString(); // Auto-generate unique ID
        this.remark = remark;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.date = new Date();
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getRemark() { return remark; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCategory() { return category; }
    public String getUser() { return user; }
    public Date getDate() { return date; }
    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    // Methods for data related calculations or logic
    public static double getTotalExpenseByCurrency(String currency, List<Expense> expenses) {
        double totalExpense = 0.0;

        for (Expense expense : expenses) {
            if (expense.getCurrency().equals(currency)) {
                totalExpense += expense.getAmount();
            }
        }

        return totalExpense;
    }

    public static String[] getMostFrequentCategory(List<Expense> expenses) {
        Map<String, Integer> category = new HashMap<>();

        for (Expense expense : expenses) {
            String currentCategory = expense.getCategory();
            category.merge(currentCategory, 1, Integer::sum);
        }

        int maxCount = category.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        List<String> mostFrequentCategory = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : category.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostFrequentCategory.add(entry.getKey());
            }
        }

        return mostFrequentCategory.toArray(new String[0]);
    }
}