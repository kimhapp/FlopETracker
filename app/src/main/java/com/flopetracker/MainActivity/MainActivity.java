package com.flopetracker.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.flopetracker.LoginActivity;
import com.flopetracker.R;
import com.flopetracker.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_button) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
            } else if (item.getItemId() == R.id.add_button) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddExpenseFragment())
                        .commit();
            } else if (item.getItemId() == R.id.list_button) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ExpenseListFragment())
                        .commit();
            } else if (item.getItemId() == R.id.logout_button) {
                logOutUser();
            }
            return true;
        });
    }

    public void logOutUser() {
        mAuth.signOut();
        Log.d("FirebaseAuth", "User logged out!");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}