 package com.flopetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.bumptech.glide.Glide;
import com.flopetracker.repository.IApiCallback;
import com.flopetracker.repository.ExpenseRepository;
import com.flopetracker.R;
import com.flopetracker.databinding.ActivityDetailExpenseBinding;

import com.flopetracker.model.Expense;

 public class DetailExpenseActivity extends BaseActivity {
    ActivityDetailExpenseBinding binding;
    String expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityDetailExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent receiveIntent = getIntent();

        if (receiveIntent != null && receiveIntent.getExtras() != null) {
            expenseId = receiveIntent.getExtras().getString("expenseId");
        }

        binding.backHomeButton.setOnClickListener(item -> finish());

        fetchExpense(expenseId);
    }

    void fetchExpense(String expenseId) {
        new ExpenseRepository().getExpense(expenseId, new IApiCallback<>() {
            @Override
            public void onSuccess(Expense expense) {
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

    void updateUI(ActivityDetailExpenseBinding binding, Expense selectedExpense) {
        if (selectedExpense != null) {
            if (selectedExpense.getImageUrl() != null && !selectedExpense.getImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(selectedExpense.getImageUrl())
                        .placeholder(R.drawable.baseline_image_24)
                        .error(R.drawable.baseline_image_24)
                        .centerCrop()
                        .into(binding.ivImage);
            }
            String idLabel = getString(R.string.label_Id) + " " + selectedExpense.getId();
            binding.expenseId.setText(idLabel);

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