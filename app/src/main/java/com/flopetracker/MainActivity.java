package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String value = result.getData().getStringExtra("key");
                    Toast.makeText(this, "Received: " + value, Toast.LENGTH_SHORT).show();
                }
            }
        );

        binding.lastExpense.

        binding.addExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            activityResultLauncher.launch(intent);
        });

        binding.viewExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            startActivity(intent);
        });
    }
}