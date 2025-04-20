package com.flopetracker.API.Expense;

import java.util.List;
import DataFolder.ExpenseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExpenseService {
    @GET("expenses")
    Call<List<ExpenseModel>> getExpenses(@Header("X-DB-NAME") String db_guid);

    @GET("expenses/{id}")
    Call<ExpenseModel> getExpense(@Header("X-DB-NAME") String db_guid, @Path("id") String id);

    @POST("expenses")
    Call<ExpenseModel> createExpense(@Header("X-DB-NAME") String db_guid, @Body ExpenseModel expenseModel);
}
