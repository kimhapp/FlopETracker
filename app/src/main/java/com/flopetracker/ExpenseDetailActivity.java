package com.flopetracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.databinding.ActivityExpenseDetailBinding;

public class ExpenseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExpenseDetailBinding binding = ActivityExpenseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}