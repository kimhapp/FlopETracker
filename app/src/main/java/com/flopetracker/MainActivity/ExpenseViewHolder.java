package com.flopetracker.MainActivity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.flopetracker.R;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {

    TextView amountView, categoryView, dateView, remarkView;
    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);

        amountView = itemView.findViewById(R.id.amount);
        categoryView = itemView.findViewById(R.id.category);
        dateView = itemView.findViewById(R.id.date);
        remarkView = itemView.findViewById(R.id.remark);
    }
}
