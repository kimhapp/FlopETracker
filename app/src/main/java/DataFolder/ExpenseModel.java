package DataFolder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExpenseModel {
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

    public ExpenseModel(double amount, String currency, String category, String remark) {
        this.id = UUID.randomUUID().toString(); // Auto-generate unique ID
        this.remark = remark;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.date = new Date();
    }

    public String getId() { return id; }
    public String getRemark() { return remark; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCategory() { return category; }
    public String getUser() { return user; }
    public Date getDate() { return date; }

    // Methods for data related calculations or logic
    public static double getTotalExpenseByCurrency(String currency, List<ExpenseModel> expenses) {
        double totalExpense = 0.0;

        for (ExpenseModel expense : expenses) {
            if (expense.getCurrency().equals(currency)) {
                totalExpense += expense.getAmount();
            }
        }

        return totalExpense;
    }

    public static String[] getMostFrequentCategory(List<ExpenseModel> expenses) {
        Map<String, Integer> category = new HashMap<>();

        for (ExpenseModel expense : expenses) {
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