package com.flopetracker.MainActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flopetracker.R;
import java.util.List;

import DataFolder.ExpenseData;
import DataFolder.ExpenseItem;

public class ExpenseListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        List<ExpenseItem> expenseItemList = ExpenseData.getDummyExpenses();

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ExpenseAdapter(expenseItemList, requireContext()));
        Log.d(String.valueOf(this), "HElo");
        return view;
    }
}