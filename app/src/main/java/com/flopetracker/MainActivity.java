package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.flopetracker.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    FragmentTransaction transaction;

    final String[] expenseLabel = {"amount", "currency", "category", "remark", "created_date"};
    String[] expenseDetails = new String[expenseLabel.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();

        Arrays.fill(expenseDetails, "");

        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    for (int i = 0; i < expenseLabel.length; i ++) {
                        expenseDetails[i] = result.getData().getStringExtra(expenseLabel[i]);
                    }
                }
            }
        );

        binding.addExpenseButton.setOnClickListener(v ->
            activityResultLauncher.launch(new Intent(this, AddExpenseActivity.class)));

        binding.viewExpenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExpenseDetailActivity.class);
            for (int i = 0; i < expenseLabel.length; i ++) {
                intent.putExtra(expenseLabel[i], expenseDetails[i]);
            }
            activityResultLauncher.launch(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String lastExpenseAmountLabel = getString(R.string.label_last_expense) + " " +
                (expenseDetails[0] != null && !expenseDetails[0].isEmpty() ? expenseDetails[0] + " " + expenseDetails[1] : " 0");
        binding.lastExpenseAmount.setText(lastExpenseAmountLabel);
    }
}