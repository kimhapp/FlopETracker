package com.flopetracker.API;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
