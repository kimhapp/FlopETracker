package com.flopetracker.activity;

import android.os.Bundle;

import com.flopetracker.databinding.ActivityNewCategoryBinding;

public class NewCategoryActivity extends BaseActivity {
    ActivityNewCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}