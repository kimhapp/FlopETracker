package com.flopetracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
