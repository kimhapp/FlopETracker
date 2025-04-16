 package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.MainActivity.MainActivity;
import com.flopetracker.databinding.ActivityDetailExpenseBinding;
import DataFolder.ExpenseData;
import DataFolder.ExpenseItem;

 public class DetailExpenseActivity extends AppCompatActivity {
    ActivityDetailExpenseBinding binding;
    String expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent receiveIntent = getIntent();

        if (receiveIntent != null && receiveIntent.getExtras() != null) {
            expenseId = receiveIntent.getExtras().getString("expenseId");
        }

        ExpenseItem selectedExpense = ExpenseData.getExpenseById(expenseId);

        binding.backHomeButton.setOnClickListener(item -> startActivity(new Intent(this, MainActivity.class)));

        if (selectedExpense != null) {
            String IdLabel = getString(R.string.label_Id) + " " + expenseId;
            binding.id.setText(IdLabel);

            String amountLabel = getString(R.string.label_amount) + ": " + selectedExpense.getAmount() + " " + selectedExpense.getCurrency();
            binding.amount.setText(amountLabel);

            String categoryLabel = getString(R.string.label_category) + ": " + selectedExpense.getCategory();
            binding.category.setText(categoryLabel);

            String remarkLabel = getString(R.string.label_remark) + ": " + selectedExpense.getRemark();
            binding.remark.setText(remarkLabel);

            String dateLabel = getString(R.string.label_date) + ": " + selectedExpense.getDate();
            binding.date.setText(dateLabel);
        }
    }
}