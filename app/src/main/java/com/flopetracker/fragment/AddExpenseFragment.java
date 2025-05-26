package com.flopetracker.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import com.flopetracker.activity.NewCategoryActivity;
import com.flopetracker.dao.AppDatabase;
import com.flopetracker.dao.ICategoryDAO;
import com.flopetracker.databinding.FragmentAddExpenseBinding;
import com.flopetracker.model.Category;
import com.flopetracker.repository.IApiCallback;
import com.flopetracker.repository.ExpenseRepository;

import com.flopetracker.model.Expense;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddExpenseFragment extends Fragment {
    boolean isLoading = false;
    FragmentAddExpenseBinding binding;
    List<String> categoryNames = new ArrayList<>();
    ActivityResultLauncher<Intent> cameraLauncher, imageLauncher;
    Uri selectedImage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        requireActivity().runOnUiThread(() -> {
                            binding.ivImage.setImageURI(selectedImage);
                            binding.ivImage.setVisibility(View.VISIBLE);
                            Toast.makeText(requireContext(), "Image captured!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });

        imageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImage = result.getData().getData();
                        if (selectedImage != null) {
                            requireActivity().runOnUiThread(() -> {
                                binding.ivImage.setImageURI(selectedImage);
                                binding.ivImage.setVisibility(View.VISIBLE);
                                Toast.makeText(requireContext(), "Image selected!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                });

        loadCategories();

        binding.addCategoriesButton.setOnClickListener(v -> startActivity(new Intent(
                requireContext(), NewCategoryActivity.class
        )));

        binding.btnCaptureImage.setOnClickListener(v -> startCaptureImageActivity());
        binding.btnSelectImage.setOnClickListener(v -> startSelectImageActivity());

        binding.addExpenseButton.setOnClickListener(v -> {
            int selectedRadioId = binding.radioAmountCurrency.getCheckedRadioButtonId();
            RadioButton radioButton = binding.getRoot().findViewById(selectedRadioId);

            double amount = Double.parseDouble(Objects.requireNonNull(binding.amountInput.getText()).toString());

            Expense createdExpense = new Expense(
                    amount,
                    radioButton.getText().toString(),
                    binding.categoriesSpinner.getSelectedItem().toString(),
                    Objects.requireNonNull(binding.remarkInput.getText()).toString()
            );

            sendExpense(createdExpense);
        });

        return binding.getRoot();
    }

    void sendExpense(Expense expense) {
        isLoading = true;
        showProgressBar();

        new ExpenseRepository().createExpense(expense, new IApiCallback<>() {
            @Override
            public void onSuccess(Expense createdExpense) {
                if (selectedImage != null) {
                    StorageReference storageReference = storage.getReference()
                            .child("expense_images/" + createdExpense.getId() + ".jpg");

                    storageReference.putFile(selectedImage).
                            addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                createdExpense.setImageUrl(uri.toString());
                                new ExpenseRepository().updateExpense(createdExpense.getId(), new IApiCallback<>() {
                                    @Override
                                    public void onSuccess(Expense result) {
                                        requireActivity().runOnUiThread(() -> {
                                            Toast.makeText(requireContext(), "Expense added!", Toast.LENGTH_SHORT).show();
                                            clearUI();
                                            isLoading = false;
                                            hideProgressBar();
                                        });
                                    }

                                    @Override
                                    public void onError(String errorMessage) {

                                    }
                                });
                            })).addOnFailureListener(e -> new ExpenseRepository().deleteExpense(createdExpense.getId(), new IApiCallback<>() {
                                @Override
                                public void onSuccess(Expense result) {
                                    requireActivity().runOnUiThread(() -> {
                                        Toast.makeText(requireContext(), "Expense fail to upload image!", Toast.LENGTH_SHORT).show();
                                        isLoading = false;
                                        hideProgressBar();
                                    });
                                }

                                @Override
                                public void onError(String errorMessage) {

                                }
                            }));
                }
                else {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "Expense added!", Toast.LENGTH_SHORT).show();
                        clearUI();
                    });
                }
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
        binding.amountInput.setText("");
        binding.radioAmountCurrency.clearCheck();
        binding.categoriesSpinner.setSelection(0);
        binding.remarkInput.setText("");
        binding.ivImage.setImageURI(null);
        binding.ivImage.setVisibility(View.GONE);
    }

    private void loadCategories() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            ICategoryDAO categoryDAO = db.categoryDAO();
            List<Category> categories = categoryDAO.getAllCategories();

            if (categories.isEmpty()) {
                categories = Category.createDefault();

                for (Category category: categories) {
                    categoryDAO.insertCategory(category);
                }
            }

            for (Category category : categories) {
                categoryNames.add(category.getName());
            }

            requireActivity().runOnUiThread(this::setUpAdapter);
        }).start();
    }

    private void setUpAdapter() {
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categoryNames
        );
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categoriesSpinner.setAdapter(categoriesAdapter);
    }

    private void startSelectImageActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(selectedImage,"image/*");
        imageLauncher.launch(intent);
    }

    private void startCaptureImageActivity() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            selectedImage = createUri();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);
            cameraLauncher.launch(cameraIntent);
        }
    }

    private Uri createUri() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File imageFile = new File(requireContext().getFilesDir(), "expense_img_" + timestamp + ".jpg");

        return FileProvider.getUriForFile(
                requireContext(),
                "com.flopetracker.fileProvider",
                imageFile
        );
    }
    private void showProgressBar() {
        if (binding != null) {
            binding.expenseProgressBar.setVisibility(View.VISIBLE);
            binding.addExpenseForm.setVisibility(View.GONE);
        }
    }

    private void hideProgressBar() {
        if (binding != null) {
            binding.expenseProgressBar.setVisibility(View.GONE);
            binding.addExpenseForm.setVisibility(View.VISIBLE);
        }
    }
}