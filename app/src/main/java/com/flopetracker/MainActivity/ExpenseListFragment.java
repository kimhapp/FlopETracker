package com.flopetracker.MainActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flopetracker.API.ApiCallback;
import com.flopetracker.API.Expense.ExpenseRepository;
import com.flopetracker.R;
import java.util.List;

import DataFolder.ExpenseData;
import DataFolder.ExpenseModel;

public class ExpenseListFragment extends Fragment {
    ExpenseRepository expenseRepo = new ExpenseRepository();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        expenseRepo.getExpenses(new ApiCallback<List<ExpenseModel>>() {
            @Override
            public void onSuccess(List<ExpenseModel> result) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ExpenseAdapter(ExpenseModelList, requireContext()));
        return view;
    }
}