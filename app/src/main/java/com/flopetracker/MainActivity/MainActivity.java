package com.flopetracker.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (item.getItemId() == R.id.home_button) {
                transaction.replace(R.id.fragment_container, new HomeFragment());
            }
            else if (item.getItemId() == R.id.add_button) {
                transaction.replace(R.id.fragment_container, new AddExpenseFragment());
            }
            else if (item.getItemId() == R.id.list_button) {
                transaction.replace(R.id.fragment_container, new ExpenseListFragment());
            }
            else if (item.getItemId() == R.id.logout_button) {
                logOutUser();
                return true;
            }

            transaction.addToBackStack(null);
            transaction.commit();
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