package com.flopetracker.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "Default")
    private boolean Default;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    @NonNull
    public String getId() {return id;}
    public void setId(@NonNull String id) {this.id = id;}
    public boolean getDefault() {return Default;}

    public Category(String name, boolean Default) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.Default = Default;
    }

    public static List<Category> createDefault() {
        return List.of(
                new Category("Food", true),
                new Category("Transport", true),
                new Category("Shopping", true),
                new Category("Beverage", true),
                new Category("Entertainment", true),
                new Category("Fitness", true),
                new Category("Utilities", true),
                new Category("Others", true)
            );
    }

}
