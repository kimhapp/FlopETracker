package com.flopetracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.flopetracker.databinding.ItemCategoryBinding;
import com.flopetracker.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context) {
        this.categories = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category item = categories.get(position);

        String categoryNameLabel = item.getName();

        holder.binding.tvCategoryName.setText(categoryNameLabel);

        if (item.getDefault()) {
            holder.binding.buttonDelete.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategory(List<Category> newCategories) {
        categories.clear();
        categories.addAll(newCategories);
        notifyDataSetChanged();
    }

    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
            notifyItemInserted(categories.size() - 1);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        protected ItemCategoryBinding binding;
        public ViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

