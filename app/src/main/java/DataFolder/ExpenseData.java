package DataFolder;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ExpenseData {
    private static final List<ExpenseItem> expenses = new ArrayList<>();

    static {
        try {
            expenses.add(new ExpenseItem("Lunch", 20000.0, "KHR", "Food", LocalDate.parse("2025-03-01")));
            expenses.add(new ExpenseItem("Taxi", 2.5, "USD", "Transport", LocalDate.parse("2025-03-02")));
            expenses.add(new ExpenseItem("Groceries", 80000.0, "KHR", "Shopping", LocalDate.parse("2025-03-03")));
            expenses.add(new ExpenseItem("Coffee", 3.0, "USD", "Beverage", LocalDate.parse("2025-03-04")));
            expenses.add(new ExpenseItem("Movie Ticket", 32000.0, "KHR", "Entertainment", LocalDate.parse("2025-03-05")));
            expenses.add(new ExpenseItem("Dinner", 12.0, "USD", "Food", LocalDate.parse("2025-03-06")));
            expenses.add(new ExpenseItem("Bus Fare", 6000.0, "KHR", "Transport", LocalDate.parse("2025-03-07")));
            expenses.add(new ExpenseItem("Gym Membership", 30.0, "USD", "Fitness", LocalDate.parse("2025-03-08")));
            expenses.add(new ExpenseItem("Electricity Bill", 200000.0, "KHR", "Utilities", LocalDate.parse("2025-03-09")));
            expenses.add(new ExpenseItem("Internet Bill", 25.0, "USD", "Utilities", LocalDate.parse("2025-03-10")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ExpenseItem> getDummyExpenses() {
        return expenses;
    }

    public static ExpenseItem getExpenseById(String id) {
        for (ExpenseItem expense : expenses) {
            if (expense.getId().equals(id)) {
                return expense;
            }
        }
        return null;
    }

    public static double getTotalExpenseByCurrency(String currency) {
        double totalExpense = 0.0;

        for (ExpenseItem expense : expenses) {
            if (expense.getCurrency().equals(currency)) {
                totalExpense += expense.getAmount();
            }
        }

        return totalExpense;
    }

    public static String getMostSpentCategory() {
        HashMap<String, Integer> category = new HashMap<>();

        for (ExpenseItem expense : expenses) {
            if (expense.getCurrency() != null) {
                category.put(expense.getCategory(), category.getOrDefault(expense.getCategory(), 0) + 1);
            }
        }

        category.forEach((k, v) ->
            if () {

            }
        );
    }
}