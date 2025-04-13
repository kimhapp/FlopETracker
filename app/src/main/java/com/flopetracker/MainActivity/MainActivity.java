package com.flopetracker.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.R;
import com.flopetracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();


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