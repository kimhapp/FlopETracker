package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.databinding.ActivityAddExpenseBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class AddExpenseActivity extends AppCompatActivity {
    final String[] expenseLabel = {"amount", "currency", "category", "remark", "created_date"};
    String[] expenseDetails = new String[expenseLabel.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddExpenseBinding binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Arrays.fill(expenseDetails, "");

        Spinner categoriesSpinner = binding.categoriesSpinner;

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        );
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoriesAdapter);

        binding.addExpenseButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();

            expenseDetails[0] = Objects.requireNonNull(binding.amountInput.getText()).toString();

            int selectedRadioId = binding.radioAmountCurrency.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedRadioId);
            expenseDetails[1] = radioButton.getText().toString();

            expenseDetails[2] = Objects.requireNonNull(categoriesSpinner.getSelectedItem()).toString();

            expenseDetails[3] = Objects.requireNonNull(binding.remarkInput.getText()).toString();

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            expenseDetails[4] = now.format(formatter);

            for (int i = 0; i < expenseLabel.length; i++) {
                resultIntent.putExtra(expenseLabel[i], expenseDetails[i]);
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}