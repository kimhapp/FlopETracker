package com.flopetracker.util;

import android.content.Context;

public class AuthHelper {
    private static final String PREF_NAME = "auth";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public void saveAuthState(Context context, String userId) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                .putString(KEY_USER_ID, userId)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public void clearAuthState(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                .clear()
                .apply();
    }

    public String getUserId(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .getString(KEY_USER_ID, null);
    }

    public boolean isLoggedIn(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
