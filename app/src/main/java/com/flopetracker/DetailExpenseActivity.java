 package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flopetracker.API.ApiCallback;
import com.flopetracker.API.Expense.ExpenseRepository;
import com.flopetracker.MainActivity.MainActivity;
import com.flopetracker.databinding.ActivityDetailExpenseBinding;

import DataFolder.ExpenseModel;

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

        binding.backHomeButton.setOnClickListener(item -> startActivity(new Intent(this, MainActivity.class)));

        fetchExpense(expenseId);
    }

    void fetchExpense(String expenseId) {
        new ExpenseRepository().getExpense(expenseId, new ApiCallback<>() {
            @Override
            public void onSuccess(ExpenseModel expense) {
                runOnUiThread(() -> updateUI(binding, expense));
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() ->
                        Toast.makeText(getApplicationContext(), "Fail to get expense!", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    void updateUI(ActivityDetailExpenseBinding binding, ExpenseModel selectedExpense) {
        if (selectedExpense != null) {
            String idLabel = getString(R.string.label_Id) + " " + selectedExpense.getId();
            binding.id.setText(idLabel);

            String amountLabel = getString(R.string.label_amount) + ": " + selectedExpense.getAmount() + " " + selectedExpense.getCurrency();
            binding.amount.setText(amountLabel);

            String categoryLabel = getString(R.string.label_category) + ": " + selectedExpense.getCategory();
            binding.category.setText(categoryLabel);

            String createdByLabel = getString(R.string.label_created_by) + ": " + selectedExpense.getUser();
            binding.createdBy.setText(createdByLabel);

            String dateLabel = getString(R.string.label_date) + ": " + selectedExpense.getDate();
            binding.date.setText(dateLabel);

            String remarkLabel = getString(R.string.label_remark) + ": " + selectedExpense.getRemark();
            binding.remark.setText(remarkLabel);
        }
    }
}