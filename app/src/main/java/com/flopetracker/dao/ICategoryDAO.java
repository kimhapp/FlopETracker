package com.flopetracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.flopetracker.model.Category;

import java.util.List;

@Dao
public interface ICategoryDAO {
    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();

    @Insert
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM categories WHERE name = :categoryName")
    Category getCategoryByName(String categoryName);
}
