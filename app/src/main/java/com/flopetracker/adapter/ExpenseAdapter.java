package com.flopetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flopetracker.R;
import com.flopetracker.activity.DetailExpenseActivity;

import java.util.ArrayList;
import java.util.List;

import com.flopetracker.databinding.ItemExpenseBinding;
import com.flopetracker.model.Expense;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    Context context;
    List<Expense> expenses;

    public ExpenseAdapter(Context context) {
        this.expenses = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExpenseBinding binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense item = expenses.get(position);

        String amountLabel = context.getString(R.string.label_amount) + ": " + item.getAmount() + " " + item.getCurrency();
        String categoryLabel = context.getString(R.string.label_category) + ": " + item.getCategory();

        holder.binding.amount.setText(amountLabel);
        holder.binding.category.setText(categoryLabel);
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_image_24)
                    .centerCrop()
                    .into(holder.binding.ivImage);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailExpenseActivity.class);
            intent.putExtra("expenseId", item.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpense(List<Expense> newExpenses) {
        expenses.clear();
        expenses.addAll(newExpenses);
        notifyDataSetChanged();
    }

    public void addExpenses(List<Expense> newExpenses) {
        int startPosition = expenses.size();
        expenses.addAll(newExpenses);
        notifyItemRangeInserted(startPosition, newExpenses.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        protected ItemExpenseBinding binding;
        public ViewHolder(@NonNull ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

