package com.flopetracker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.databinding.ActivityMainBinding;

public class SecondActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.returnButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("key", "value");
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}