package com.flopetracker.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flopetracker.API.ApiCallback;
import com.flopetracker.API.Expense.ExpenseRepository;
import com.flopetracker.R;

import java.util.ArrayList;
import java.util.List;

import DataFolder.ExpenseModel;

public class ExpenseListFragment extends Fragment {
    ExpenseAdapter adapter;
    List<ExpenseModel> expenseList = new ArrayList<>();
    boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new ExpenseAdapter(expenseList, requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    fetchExpenses();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchExpenses();
    }

    void fetchExpenses() {
        if (isLoading) return;

        isLoading = true;
        if (expenseList.isEmpty()) {
            Toast.makeText(requireContext(), "Loading expenses...", Toast.LENGTH_SHORT).show();
        }

        new ExpenseRepository().getExpenses(new ApiCallback<>() {
            @Override
            public void onSuccess(List<ExpenseModel> expenses) {
                requireActivity().runOnUiThread(() -> {
                    int oldSize = expenseList.size();
                    int newItems = Math.min(expenses.size() - oldSize, 4);

                    if (newItems > 0) {
                        for (int i = 0; i < newItems; i++) {
                            expenseList.add(expenses.get(oldSize + i));
                        }
                        adapter.notifyItemRangeInserted(oldSize, newItems);
                    } else if (expenseList.isEmpty()) {
                        Toast.makeText(requireContext(), "No expenses found", Toast.LENGTH_SHORT).show();
                    } else if (!expenses.isEmpty()) {
                        Toast.makeText(requireContext(), "No more expenses", Toast.LENGTH_SHORT).show();
                    }

                    isLoading = false;
                });
            }

            @Override
            public void onError(String errorMessage) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Failed to load", Toast.LENGTH_SHORT).show();
                    isLoading = false;
                });
            }
        });
    }
}