package com.flopetracker.repository;

import com.flopetracker.service.ExpenseService;
import com.flopetracker.util.DbGUID;
import com.flopetracker.util.RetroFitClient;

import java.util.List;

import com.flopetracker.model.Expense;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseRepository {
    String db_guid = DbGUID.getDbGuid();
    private final ExpenseService expenseService;

    public ExpenseRepository() {
        expenseService = RetroFitClient.getClient().create(ExpenseService.class);
    }

    public void getExpenses(final IApiCallback<List<Expense>> callback) {
        Call<List<Expense>> call = expenseService.getExpenses(db_guid);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable throwable) {
                callback.onError("Network Error: " + throwable.getMessage());
            }
        });
    }

    public void getExpense(String id, final IApiCallback<Expense> callback) {
        Call<Expense> call = expenseService.getExpense(db_guid, id);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable throwable) {
                callback.onError("Network Error: " + throwable.getMessage());
            }
        });
    }

    public void createExpense(Expense expense, final IApiCallback<Expense> callback) {
        Call<Expense> call = expenseService.createExpense(db_guid, expense);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
                else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable throwable) {
                callback.onError("Network Error: " + throwable.getMessage());
            }
        });
    }
}
