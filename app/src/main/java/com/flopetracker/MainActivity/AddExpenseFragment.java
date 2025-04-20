package com.flopetracker.MainActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.flopetracker.API.ApiCallback;
import com.flopetracker.API.Expense.ExpenseRepository;
import com.flopetracker.R;
import com.google.android.material.textfield.TextInputEditText;

import DataFolder.ExpenseModel;

public class AddExpenseFragment extends Fragment {
    TextInputEditText amountInput;
    RadioGroup radioAmountCurrency;
    Spinner categoriesSpinner;
    TextInputEditText remarkInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        categoriesSpinner = view.findViewById(R.id.categories_spinner);

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories_array,
                android.R.layout.simple_spinner_item
        );
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoriesAdapter);

        Button add_button = view.findViewById(R.id.add_expense_button);

        add_button.setOnClickListener(v -> {
            amountInput = view.findViewById(R.id.amount_input);

            radioAmountCurrency = view.findViewById(R.id.radio_amount_currency);
            int selectedRadioId = radioAmountCurrency.getCheckedRadioButtonId();
            RadioButton radioButton = view.findViewById(selectedRadioId);

            remarkInput = view.findViewById(R.id.remark_input);

            double amount = Double.parseDouble(amountInput.getText().toString());

            ExpenseModel createdExpense = new ExpenseModel(
                    amount,
                    radioButton.getText().toString(),
                    categoriesSpinner.getSelectedItem().toString(),
                    remarkInput.getText().toString()
            );

            sendExpense(createdExpense);
        });

        return view;
    }

    void sendExpense(ExpenseModel expense) {
        new ExpenseRepository().createExpense(expense, new ApiCallback<>() {
            @Override
            public void onSuccess(ExpenseModel createdExpense) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Expense added!", Toast.LENGTH_SHORT).show();
                    clearUI();
                });
            }

            @Override
            public void onError(String errorMessage) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Fail to add expense!", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    void clearUI() {
        amountInput.setText("");
        radioAmountCurrency.clearCheck();
        categoriesSpinner.setSelection(0);
        remarkInput.setText("");
    }
}