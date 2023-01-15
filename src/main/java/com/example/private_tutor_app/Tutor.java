package com.example.private_tutor_app;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Tutor implements Serializable {
    private int id_tutor;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String school;
    private String gender;
    private String yob;
    private String subject;
    private String experience;
    private Bitmap photo;

    public Tutor(int id_tutor, String fullName, String email, String password, String phoneNumber, String address, String school, String gender, String yob, String subject, String experience, Bitmap photo) {
        this.id_tutor = id_tutor;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.school = school;
        this.gender = gender;
        this.yob = yob;
        this.subject = subject;
        this.experience = experience;
        this.photo = photo;
    }

    public Tutor(int id_tutor, String fullName, String email, String password
            , String phoneNumber, String address, String school, String gender, String yob, String subject, String experience) {
        this.id_tutor = id_tutor;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.school = school;
        this.gender = gender;
        this.yob = yob;
        this.subject = subject;
        this.experience = experience;
    }

    public Tutor(String fullName, String email, String password,
                 String phoneNumber, String address, String school, String gender, String yob, String subject, String experience) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.school = school;
        this.gender = gender;
        this.yob = yob;
        this.subject = subject;
        this.experience = experience;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public int getId_tutor() {
        return id_tutor;
    }

    public void setId_tutor(int id_tutor) {
        this.id_tutor = id_tutor;
    }

    public String getYob() {
        return yob;
    }

    public void setYob(String yob) {
        this.yob = yob;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
