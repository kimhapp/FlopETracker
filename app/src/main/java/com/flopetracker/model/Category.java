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

    @ColumnInfo(name = "Default")
    private boolean Default;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public boolean getDefault() {return Default;}
    public void setDefault(boolean Default) {this.Default = Default;}

    public Category(int id, String name, boolean Default) {
        this.id = id;
        this.name = name;
        this.Default = Default;
    }
}
