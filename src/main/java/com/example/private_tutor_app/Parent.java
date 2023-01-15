package com.example.private_tutor_app;

import android.graphics.Bitmap;
import android.net.Uri;

public class Parent {
    private int id_parent;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Bitmap photo;
    private Uri img;

    public Parent(int id_parent, String fullName, String email, String password, String phoneNumber, String address, Uri img) {
        this.id_parent = id_parent;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.img = img;
    }

    public Parent(int id_parent, String fullName, String email, String password, String phoneNumber, String address, Bitmap photo) {
        this.id_parent = id_parent;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public int getId_parent() {
        return id_parent;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
