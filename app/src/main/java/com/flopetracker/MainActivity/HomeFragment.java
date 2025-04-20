package com.flopetracker.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flopetracker.API.ApiCallback;
import com.flopetracker.API.Expense.ExpenseRepository;
import com.flopetracker.R;

import java.util.List;

import DataFolder.ExpenseModel;

public class HomeFragment extends Fragment {
    TextView text_KHR, text_USD, mostSpentCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        text_KHR = view.findViewById(R.id.USD_total);
        text_USD = view.findViewById(R.id.KHR_total);
        mostSpentCategory = view.findViewById(R.id.most_spent_category);

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
                String[] categories = ExpenseModel.getMostFrequentCategory(expenses);
                double total_KHR = ExpenseModel.getTotalExpenseByCurrency("KHR", expenses);
                double total_USD = ExpenseModel.getTotalExpenseByCurrency("USD", expenses);

                requireActivity().runOnUiThread(() -> updateUI(total_USD, total_KHR, categories));
            }

            @Override
            public void onError(String errorMessage) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Fail to get expenses!", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    void updateUI(double total_USD, double total_KHR, String[] categories) {
        String USD = getString(R.string.label_total_USD) + " " + total_USD + " USD";
        String KHR = getString(R.string.label_total_KHR) + " " + total_KHR + " KHR";
        String MSC = getString(R.string.label_most_spent_category) + " " + String.join(", ", categories);

        text_USD.setText(USD);
        text_KHR.setText(KHR);
        mostSpentCategory.setText(MSC);
    }
}