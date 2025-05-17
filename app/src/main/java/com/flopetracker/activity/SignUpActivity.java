package com.flopetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.flopetracker.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            reload();
        }
    }

    private void reload() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.signUpButton.setOnClickListener(v -> {
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();
            String confirmPassword = binding.confirmPasswordInput.getText().toString();

            if (password.equals(confirmPassword)) {
                registerUser(email, password);
            }
            else {
                Log.e(this.toString(), "Confirm password doesn't match password!");
            }
        });

        binding.logInLink.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                Log.d("FirebaseAuth", "User registered: " + user.getEmail());
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else {
                Log.e("FirebaseAuth" ,"Registration failed", task.getException());
            }
        });
    }
}