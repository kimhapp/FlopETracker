package com.flopetracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.flopetracker.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}