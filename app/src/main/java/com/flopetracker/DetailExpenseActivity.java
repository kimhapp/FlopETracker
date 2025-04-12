 package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.flopetracker.MainActivity.AddExpenseFragment;
import com.flopetracker.MainActivity.ExpenseListFragment;
import com.flopetracker.MainActivity.HomeFragment;
import com.flopetracker.databinding.ActivityDetailExpenseBinding;

import java.util.Arrays;

 public class DetailExpenseActivity extends AppCompatActivity {
    ActivityDetailExpenseBinding binding;

    final String[] expenseLabel = {"id","amount", "currency", "category", "date", "remark"};
    String[] expenseDetails = new String[expenseLabel.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Arrays.fill(expenseDetails, "");

        Intent receiveIntent = getIntent();

        if (receiveIntent != null && receiveIntent.getExtras() != null) {
            for (int i = 0; i < expenseLabel.length; i++) {
                expenseDetails[i] = receiveIntent.getExtras().getString(expenseLabel[i]);
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_button) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
            } else if (item.getItemId() == R.id.add_button) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddExpenseFragment())
                        .commit();
            } else if (item.getItemId() == R.id.list_button) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ExpenseListFragment())
                        .commit();
            }
            return true;
        });

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

        String createdDateLabel = getString(R.string.label_date) + ": " + expenseDetails[4];
        binding.date.setText(createdDateLabel);
    }
}