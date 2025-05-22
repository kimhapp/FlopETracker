package com.flopetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;

import com.flopetracker.fragment.AddExpenseFragment;
import com.flopetracker.fragment.ExpenseListFragment;
import com.flopetracker.fragment.HomeFragment;
import com.flopetracker.fragment.SettingFragment;
import com.flopetracker.R;
import com.flopetracker.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home_button) {
                LoadFragment(new HomeFragment());
            }
            else if (itemId == R.id.add_button) {
                LoadFragment(new AddExpenseFragment());
            }
            else if (itemId == R.id.list_button) {
                LoadFragment(new ExpenseListFragment());
            }
            else if (itemId == R.id.setting_button) {
                LoadFragment(new SettingFragment());
            }
            else if (itemId == R.id.logout_button) {
                logOutUser();
                return true;
            }
            else {
                return false;
            }

            return true;
        });

        if(savedInstanceState == null){
            binding.bottomNavigation.setSelectedItemId(R.id.home_button);
        }else{
            binding.bottomNavigation.setSelectedItemId(savedInstanceState.getInt("selectedItemId"));
        }
    }

    public void logOutUser() {
        mAuth.signOut();
        Log.d("FirebaseAuth", "User logged out!");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void LoadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}