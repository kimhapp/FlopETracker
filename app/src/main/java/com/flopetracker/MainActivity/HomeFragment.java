package com.flopetracker.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flopetracker.R;

import DataFolder.ExpenseData;

public class HomeFragment extends Fragment {
    TextView total_KHR, total_USD, mostSpentCategory;
    String[] categories = ExpenseData.getMostSpentCategory();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        total_USD = view.findViewById(R.id.USD_total);
        total_KHR = view.findViewById(R.id.KHR_total);
        mostSpentCategory = view.findViewById(R.id.most_spent_category);

        String USD = getString(R.string.label_total_USD) + " " + ExpenseData.getTotalExpenseByCurrency("USD") + " USD";
        String KHR = getString(R.string.label_total_KHR) + " " + ExpenseData.getTotalExpenseByCurrency("KHR") + " KHR";
        String MSC = getString(R.string.label_most_spent_category) + " " + String.join(", ", categories);

        total_USD.setText(USD);
        total_KHR.setText(KHR);
        mostSpentCategory.setText(MSC);

        return view;
    }
}