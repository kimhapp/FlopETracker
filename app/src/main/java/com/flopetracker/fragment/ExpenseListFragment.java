package com.flopetracker.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flopetracker.databinding.FragmentExpenseListBinding;
import com.flopetracker.repository.IApiCallback;
import com.flopetracker.repository.ExpenseRepository;
import com.flopetracker.adapter.ExpenseAdapter;

import java.util.ArrayList;
import java.util.List;

import com.flopetracker.model.Expense;

public class ExpenseListFragment extends Fragment {
    FragmentExpenseListBinding binding;
    int currentPage = 1;
    ExpenseRepository repository;
    ExpenseAdapter expenseAdapter;
    List<Expense> expenseList = new ArrayList<>();
    private static int PRE_LOAD_ITEMS = 1;
    boolean isLoading = false;

    public ExpenseListFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExpenseListBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvExpense.setLayoutManager(layoutManager);
        expenseAdapter = new ExpenseAdapter(requireContext());
        binding.rcvExpense.setAdapter(expenseAdapter);
        repository = new ExpenseRepository();

        binding.rcvExpense.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + PRE_LOAD_ITEMS)) {
                    loadExpenses(false);
                }
            }
        });

        return binding.getRoot();
    }

    void loadExpenses(boolean reset) {
        isLoading = true;
        showProgressBar();

        repository.getExpenses(new IApiCallback<>() {
            @Override
            public void onSuccess(List<Expense> expenses) {
                requireActivity().runOnUiThread(() -> {
                    if (!expenses.isEmpty()) {
                        if (reset) {
                            expenseAdapter.setExpense(expenses);
                        }
                        else {
                            expenseAdapter.addExpenses(expenses);
                        }

                        currentPage++;
                    }

                    hideProgressBar();
                    isLoading = false;
                });
            }

            @Override
            public void onError(String errorMessage) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Failed to load", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                    isLoading = false;
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPage = 1;
        loadExpenses(true);
    }

    private void showProgressBar() {
        if (binding != null) {
            binding.expenseProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (binding != null) {
            binding.expenseProgressBar.setVisibility(View.GONE);
        }
    }
}