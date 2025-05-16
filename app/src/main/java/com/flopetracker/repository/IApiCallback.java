package com.flopetracker.repository;

public interface IApiCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
