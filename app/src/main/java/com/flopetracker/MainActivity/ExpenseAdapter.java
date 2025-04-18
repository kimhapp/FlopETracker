package com.flopetracker.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flopetracker.DetailExpenseActivity;
import com.flopetracker.R;
import java.util.List;
import DataFolder.ExpenseModel;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    public ExpenseAdapter(List<ExpenseModel> ExpenseModels, Context context) {
        this.ExpenseModels = ExpenseModels;
        this.context = context;
    }

    Context context;
    List<ExpenseModel> ExpenseModels;

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseModel item = ExpenseModels.get(position);

        String amountLabel = context.getString(R.string.label_amount) + ": " + item.getAmount() + " " + item.getCurrency();
        String categoryLabel = context.getString(R.string.label_category) + ": " + item.getCategory();
        String dateLabel = context.getString(R.string.label_date) + ": " + item.getDate();
        String remarkLabel = context.getString(R.string.label_remark) + ": " + item.getRemark();

        holder.amountView.setText(amountLabel);
        holder.categoryView.setText(categoryLabel);
        holder.dateView.setText(dateLabel);
        holder.remarkView.setText(remarkLabel);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailExpenseActivity.class);
            intent.putExtra("expenseId", item.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ExpenseModels.size();
    }
}
