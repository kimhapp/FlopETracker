package com.flopetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flopetracker.R;
import com.flopetracker.adapter.CategoryAdapter;
import com.flopetracker.dao.AppDatabase;
import com.flopetracker.dao.ICategoryDAO;
import com.flopetracker.databinding.ActivityNewCategoryBinding;
import com.flopetracker.model.Category;

import java.util.List;
import java.util.Objects;

public class NewCategoryActivity extends BaseActivity {
    ActivityNewCategoryBinding binding;
    CategoryAdapter categoryAdapter;
    ICategoryDAO categoryDAO;
    boolean isLoading = false;
    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityNewCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this);
        binding.rcvCategory.setAdapter(categoryAdapter);

        binding.homeButton.setOnClickListener(item -> finish());

        binding.addButton.setOnClickListener(item -> {
            String name = Objects.requireNonNull(binding.categoryInput.getText()).toString();
            binding.rcvCategory.setVisibility(View.GONE);
            insertCategory(name);
            binding.categoryInput.setText("");
        });

        binding.showExistingCategoryButton.setOnClickListener(item -> {
            binding.showExistingCategoryButton.setVisibility(View.GONE);
            binding.hideExistingCategoryButton.setVisibility(View.VISIBLE);

            loadCategories(true, null);
        });

        binding.hideExistingCategoryButton.setOnClickListener(item -> {
            binding.rcvCategory.setVisibility(View.GONE);
            binding.hideExistingCategoryButton.setVisibility(View.GONE);
            binding.showExistingCategoryButton.setVisibility(View.VISIBLE);
        });
    }

    private void loadCategories(boolean reset, @Nullable Category category) {
        isLoading = true;
        showProgressBar();

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(NewCategoryActivity.this);
            categoryDAO = db.categoryDAO();
            categories = categoryDAO.getAllCategories();

            runOnUiThread(() -> {
                if (!categories.isEmpty()) {
                    if (reset) {
                        categoryAdapter.setCategory(categories);
                    }
                    else {
                        categoryAdapter.addCategory(category);
                    }
                }
                hideProgressBar();
                isLoading = false;

                binding.rcvCategory.setVisibility(View.VISIBLE);
            });
        }).start();
    }

    private void insertCategory(String name) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(NewCategoryActivity.this);
            categoryDAO = db.categoryDAO();
            categories = categoryDAO.getAllCategories();

            Category category = new Category(name, false);
            categoryDAO.insertCategory(category);

            runOnUiThread(() -> loadCategories(false, category));
        }).start();
    }

    private void showProgressBar() {
        if (binding != null) {
            binding.categoryProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (binding != null) {
            binding.categoryProgressBar.setVisibility(View.GONE);
        }
    }
}