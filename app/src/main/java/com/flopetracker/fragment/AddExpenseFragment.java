package com.flopetracker.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.flopetracker.activity.NewCategoryActivity;
import com.flopetracker.dao.AppDatabase;
import com.flopetracker.dao.ICategoryDAO;
import com.flopetracker.model.Category;
import com.flopetracker.repository.IApiCallback;
import com.flopetracker.repository.ExpenseRepository;
import com.flopetracker.R;
import com.google.android.material.textfield.TextInputEditText;

import com.flopetracker.model.Expense;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddExpenseFragment extends Fragment {
    TextInputEditText amountInput;
    RadioGroup radioAmountCurrency;
    Spinner categoriesSpinner;
    TextInputEditText remarkInput;
    ImageView imageView;
    Button add_button, selectImage, captureImage;
    List<String> categoryNames = new ArrayList<>();
    ActivityResultLauncher<Intent> cameraLauncher, imageLauncher;
    Uri selectedImage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        requireActivity().runOnUiThread(() -> {
                            imageView.setImageURI(selectedImage);
                            imageView.setVisibility(View.VISIBLE);
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
                                imageView.setImageURI(selectedImage);
                                imageView.setVisibility(View.VISIBLE);
                                Toast.makeText(requireContext(), "Image selected!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                });

        categoriesSpinner = view.findViewById(R.id.categories_spinner);
        ImageButton add_category_button = view.findViewById(R.id.add_categories_button);
        add_button = view.findViewById(R.id.add_expense_button);
        selectImage = view.findViewById(R.id.btn_select_image);
        captureImage = view.findViewById(R.id.btn_capture_image);
        imageView = view.findViewById(R.id.iv_image);

        loadCategories();

        add_category_button.setOnClickListener(v -> startActivity(new Intent(
                requireContext(), NewCategoryActivity.class
        )));

        captureImage.setOnClickListener(v -> startCaptureImageActivity());
        selectImage.setOnClickListener(v -> startSelectImageActivity());

        add_button.setOnClickListener(v -> {
            amountInput = view.findViewById(R.id.amount_input);

            radioAmountCurrency = view.findViewById(R.id.radio_amount_currency);
            int selectedRadioId = radioAmountCurrency.getCheckedRadioButtonId();
            RadioButton radioButton = view.findViewById(selectedRadioId);

            remarkInput = view.findViewById(R.id.remark_input);

            double amount = Double.parseDouble(amountInput.getText().toString());

            Expense createdExpense = new Expense(
                    amount,
                    radioButton.getText().toString(),
                    categoriesSpinner.getSelectedItem().toString(),
                    remarkInput.getText().toString(),
                    selectedImage.toString()
            );

            sendExpense(createdExpense);
        });

        return view;
    }

    void sendExpense(Expense expense) {
        new ExpenseRepository().createExpense(expense, new IApiCallback<>() {
            @Override
            public void onSuccess(Expense createdExpense) {
                if (selectedImage != null) {
                    StorageReference storageReference = storage.getReference()
                            .child("expense_images/" + createdExpense.getId() + ".jpg");

                    storageReference.putFile(selectedImage).
                            addOnSuccessListener(taskSnapshot -> {
                                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                    createdExpense.setImageUrl(uri.toString());
                                    new ExpenseRepository().updateExpense(createdExpense.getId(), new IApiCallback<Expense>() {
                                        @Override
                                        public void onSuccess(Expense result) {
                                            requireActivity().runOnUiThread(() -> {
                                                Toast.makeText(requireContext(), "Expense added!", Toast.LENGTH_SHORT).show();
                                                clearUI();
                                            });
                                        }

                                        @Override
                                        public void onError(String errorMessage) {

                                        }
                                    });
                                });
                            });
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
        amountInput.setText("");
        radioAmountCurrency.clearCheck();
        categoriesSpinner.setSelection(0);
        remarkInput.setText("");
        imageView.setImageURI(null);
        imageView.setVisibility(View.GONE);
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
        categoriesSpinner.setAdapter(categoriesAdapter);
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
}