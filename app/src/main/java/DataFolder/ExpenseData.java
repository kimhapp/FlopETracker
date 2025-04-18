package DataFolder;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseData {
    private static final List<ExpenseModel> expenses = new ArrayList<>();

    static {
        try {
            expenses.add(new ExpenseModel("Lunch", 20000.0, "KHR", "Food", LocalDate.parse("2025-03-01")));
            expenses.add(new ExpenseModel("Taxi", 2.5, "USD", "Transport", LocalDate.parse("2025-03-02")));
            expenses.add(new ExpenseModel("Groceries", 80000.0, "KHR", "Shopping", LocalDate.parse("2025-03-03")));
            expenses.add(new ExpenseModel("Coffee", 3.0, "USD", "Beverage", LocalDate.parse("2025-03-04")));
            expenses.add(new ExpenseModel("Movie Ticket", 32000.0, "KHR", "Entertainment", LocalDate.parse("2025-03-05")));
            expenses.add(new ExpenseModel("Dinner", 12.0, "USD", "Food", LocalDate.parse("2025-03-06")));
            expenses.add(new ExpenseModel("Bus Fare", 6000.0, "KHR", "Transport", LocalDate.parse("2025-03-07")));
            expenses.add(new ExpenseModel("Gym Membership", 30.0, "USD", "Fitness", LocalDate.parse("2025-03-08")));
            expenses.add(new ExpenseModel("Electricity Bill", 200000.0, "KHR", "Utilities", LocalDate.parse("2025-03-09")));
            expenses.add(new ExpenseModel("Internet Bill", 25.0, "USD", "Utilities", LocalDate.parse("2025-03-10")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ExpenseModel> getDummyExpenses() {
        return expenses;
    }

    public static ExpenseModel getExpenseById(String id) {
        for (ExpenseModel expense : expenses) {
            if (expense.getId().equals(id)) {
                return expense;
            }
        }
        return null;
    }

    public static double getTotalExpenseByCurrency(String currency) {
        double totalExpense = 0.0;

        for (ExpenseModel expense : expenses) {
            if (expense.getCurrency().equals(currency)) {
                totalExpense += expense.getAmount();
            }
        }

        return totalExpense;
    }

    public static String[] getMostSpentCategory() {
        HashMap<String, Integer> category = new HashMap<>();
        int currentHighNumber = 0;

        for (ExpenseModel expense : expenses) {
            String currentCategory = expense.getCategory();
            int newCount = category.merge(currentCategory, 1, Integer::sum);

            if (newCount > currentHighNumber) {
                currentHighNumber = newCount;
            }
        }

        List<String> mostSpentCategory = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : category.entrySet()) {
            if (entry.getValue() == currentHighNumber) {
                mostSpentCategory.add(entry.getKey());
            }
        }

        return mostSpentCategory.toArray(new String[0]);
    }
}