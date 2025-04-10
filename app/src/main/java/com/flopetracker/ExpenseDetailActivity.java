 package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.databinding.ActivityExpenseDetailBinding;

import java.util.Arrays;

public class ExpenseDetailActivity extends AppCompatActivity {
    /* ActivityExpenseDetailBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;

    final String[] expenseLabel = {"amount", "currency", "category", "remark", "created_date"};
    String[] expenseDetails = new String[expenseLabel.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Arrays.fill(expenseDetails, "");

        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    setResult(RESULT_OK, result.getData());
                    finish();
                }
            }
        );

        Intent receiveIntent = getIntent();

        if (receiveIntent != null && receiveIntent.getExtras() != null) {
            for (int i = 0; i < expenseLabel.length; i++) {
                expenseDetails[i] = receiveIntent.getExtras().getString(expenseLabel[i]);
            }
        }

        binding.addExpenseButton.setOnClickListener(v ->
            activityResultLauncher.launch(new Intent(this, AddExpenseActivity.class)));

        binding.backHomeButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();

        String amountLabel = getString(R.string.label_amount) + ": " + expenseDetails[0] + " " + expenseDetails[1];
        binding.amount.setText(amountLabel);

        String categoryLabel = getString(R.string.label_category) + ": " + expenseDetails[2];
        binding.category.setText(categoryLabel);

        String remarkLabel = getString(R.string.label_remark) + ": " + expenseDetails[3];
        binding.remark.setText(remarkLabel);

        String createdDateLabel = getString(R.string.label_created_date) + ": " + expenseDetails[4];
        binding.createdDate.setText(createdDateLabel);
    } */
}