package com.flopetracker.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.R;
import com.flopetracker.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;

    final String[] expenseLabel = {"amount", "currency", "category", "remark", "created_date"};
    String[] expenseDetails = new String[expenseLabel.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        /* Arrays.fill(expenseDetails, "");

        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    for (int i = 0; i < expenseLabel.length; i ++) {
                        expenseDetails[i] = result.getData().getStringExtra(expenseLabel[i]);
                    }
                }
            }
        ); */

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
}