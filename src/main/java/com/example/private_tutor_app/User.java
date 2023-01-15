package com.example.private_tutor_app;

import android.graphics.Bitmap;

public class User {
    private String id;
    private String fullname;
    private String email;
    private String password;
    private String role;
    private String imageUrl;
    private String token;
    private Bitmap photo;

    public User() {
    }

    public User(String id, String fullname, String email, String password, String role, String urlImg) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.imageUrl = urlImg;
    }

    public User(String id, String fullname, String email, String password, String role, Bitmap photo) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.photo = photo;
    }

    public User(String id, String fullname, String email, String role, String urlImg) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.imageUrl = urlImg;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
