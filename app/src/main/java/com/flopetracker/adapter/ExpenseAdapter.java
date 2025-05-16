package com.flopetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        String dateLabel = context.getString(R.string.label_date) + ": " + item.getDate();
        String remarkLabel = context.getString(R.string.label_remark) + ": " + item.getRemark();
        String createdByLabel = context.getString(R.string.label_created_by) + ": " + item.getUser();

        holder.binding.amount.setText(amountLabel);
        holder.binding.category.setText(categoryLabel);
        holder.binding.date.setText(dateLabel);
        holder.binding.remark.setText(remarkLabel);
        holder.binding.createdBy.setText(createdByLabel);

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

