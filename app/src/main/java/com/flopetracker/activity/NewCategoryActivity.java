package com.flopetracker.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import com.flopetracker.databinding.ActivityNewCategoryBinding;

public class NewCategoryActivity extends BaseActivity {
    ActivityNewCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityNewCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.homeButton.setOnClickListener(item -> startActivity(new Intent(this, MainActivity.class)));
    }
}