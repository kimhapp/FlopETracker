package DataFolder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
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

    public ExpenseModel(String remark, double amount, String currency, String category) {
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
}