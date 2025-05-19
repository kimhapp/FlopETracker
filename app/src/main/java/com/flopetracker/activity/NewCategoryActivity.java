package com.flopetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flopetracker.R;
import com.flopetracker.adapter.CategoryAdapter;
import com.flopetracker.adapter.ExpenseAdapter;
import com.flopetracker.dao.AppDatabase;
import com.flopetracker.dao.ICategoryDAO;
import com.flopetracker.databinding.ActivityNewCategoryBinding;
import com.flopetracker.model.Category;

import java.util.List;

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

        binding.homeButton.setOnClickListener(item -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("selectedItemId", R.id.add_button);

            startActivity(intent);
        });

        binding.showExistingCategoryButton.setOnClickListener(item -> {
            binding.rcvCategory.setVisibility(View.VISIBLE);
            binding.showExistingCategoryButton.setVisibility(View.INVISIBLE);
            binding.hideExistingCategoryButton.setVisibility(View.VISIBLE);

        });
    }

    private void loadCategories(boolean reset) {
        isLoading = true;
        showProgressBar();

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(NewCategoryActivity.this);
                categoryDAO = db.categoryDAO();
                categories = categoryDAO.getAllCategories();

                if (!categories.isEmpty()) {
                    if (reset) {
                        categoryAdapter.setCategory(categories);
                    }
                    else {
                        categoryAdapter.addCategory(categories);
                    }
                }
            }
        }).start();

        hideProgressBar();
        isLoading = false;
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