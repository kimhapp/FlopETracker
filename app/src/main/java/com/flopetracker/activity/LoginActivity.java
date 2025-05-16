package com.flopetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.flopetracker.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
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
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.logInButton.setOnClickListener(v -> {
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();
            loginUser(email, password);
        });

        binding.signUpLink.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               FirebaseUser user = mAuth.getCurrentUser();
               assert user != null;
               Log.d("FirebaseAuth", "Login successful: " + user.getEmail());
               startActivity(new Intent(this, MainActivity.class));
               finish();
           }
           else {
               Log.e("FirebaseAuth", "Login failed!", task.getException());
           }
        });
    }
}