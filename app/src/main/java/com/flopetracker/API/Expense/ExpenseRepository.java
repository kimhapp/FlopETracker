package com.flopetracker.API.Expense;

import com.flopetracker.API.ApiCallback;
import com.flopetracker.API.DbGUID;
import com.flopetracker.API.RetroFitClient;

import java.util.List;

import DataFolder.ExpenseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseRepository {
    String db_guid = DbGUID.getDbGuid();
    private ExpenseService expenseService;

    public ExpenseRepository() {
        expenseService = RetroFitClient.getClient().create(ExpenseService.class);
    }

    public void getExpenses(final ApiCallback<List<ExpenseModel>> callback) {
        Call<List<ExpenseModel>> call = expenseService.getExpenses(db_guid);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<ExpenseModel>> call, Response<List<ExpenseModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ExpenseModel>> call, Throwable throwable) {
                callback.onError("Network Error: " + throwable.getMessage());
            }
        });
    }

    public void getExpense(String id, final ApiCallback<ExpenseModel> callback) {
        Call<ExpenseModel> call = expenseService.getExpense(db_guid, id);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ExpenseModel> call, Response<ExpenseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ExpenseModel> call, Throwable throwable) {
                callback.onError("Network Error: " + throwable.getMessage());
            }
        });
    }

    public void createExpense(ExpenseModel expenseModel, final ApiCallback<ExpenseModel> callback) {
        Call<ExpenseModel> call = expenseService.createExpense(db_guid, expenseModel);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ExpenseModel> call, Response<ExpenseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
                else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ExpenseModel> call, Throwable throwable) {
                callback.onError("Network Error: " + throwable.getMessage());
            }
        });
    }
}
