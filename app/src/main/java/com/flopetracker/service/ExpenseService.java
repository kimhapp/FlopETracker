package com.flopetracker.service;

import java.util.List;
import com.flopetracker.model.Expense;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExpenseService {
    @GET("expenses")
    Call<List<Expense>> getExpenses(@Header("X-DB-NAME") String db_guid);

    @GET("expenses/{id}")
    Call<Expense> getExpense(@Header("X-DB-NAME") String db_guid, @Path("id") String id);

    @POST("expenses")
    Call<Expense> createExpense(@Header("X-DB-NAME") String db_guid, @Body Expense expense);

    @PATCH("expenses/{id}")
    Call<Expense> updateExpense(@Header("X-DB-NAME") String db_guid, @Path("id") String id);

    @DELETE("expenses/{id}")
    Call<Expense> deleteExpense(@Header("X-DB-NAME") String db_guid, @Path("id") String id);
}
