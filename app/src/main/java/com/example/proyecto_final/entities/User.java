package com.example.proyecto_final.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USER")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "EMAIL")
    @NonNull
    private String email;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "USERNAME")
    private String userName;
    @ColumnInfo(name = "PHONE")
    private String phoneNumber;
    @ColumnInfo(name = "PASSWORD")
    private String password;

    public User(@NonNull String email, String name, String userName, String phoneNumber, String password) {
        this.email = email;
        this.name = name;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
