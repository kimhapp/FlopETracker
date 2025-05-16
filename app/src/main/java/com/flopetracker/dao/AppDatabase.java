package com.flopetracker.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.flopetracker.model.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract ICategoryDAO categoryDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "category_database"
            ).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
