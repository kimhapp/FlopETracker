package DataFolder;

import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpenseItem {
    private String id;
    private String remark;
    private double amount;
    private String currency;
    private String category;
    private LocalDate date;

    public ExpenseItem(String remark, double amount, String currency, String category, LocalDate date) {
        this.id = UUID.randomUUID().toString(); // Auto-generate unique ID
        this.remark = remark;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.date = date;
    }

    public String getId() { return id; }
    public String getRemark() { return remark; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }

    public String getFormattedDueDate() {
        LocalDate date = getDate();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateFormat);
    }
}