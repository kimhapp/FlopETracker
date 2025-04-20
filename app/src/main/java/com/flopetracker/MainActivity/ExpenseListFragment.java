package com.flopetracker.MainActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new ExpenseAdapter(expenseList, requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchExpenses();
    }

    void fetchExpenses() {
        new ExpenseRepository().getExpenses(new ApiCallback<>() {
            @Override
            public void onSuccess(List<ExpenseModel> expenses) {
                int oldSize = expenseList.size();
                int newSize = expenses.size();

                // Only update if there's only change in the db
                if (newSize > oldSize) {
                    requireActivity().runOnUiThread(() -> {
                        expenseList.addAll(expenses.subList(oldSize, newSize));
                        adapter.notifyItemRangeInserted(oldSize, newSize - oldSize);
                    });
                }
            }

            @Override
            public void onError(String errorMessage) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Fail to get expenses!", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}