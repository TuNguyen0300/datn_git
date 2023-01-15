package com.example.private_tutor_app;

import java.io.Serializable;

public class Class implements Serializable {
    private int id_class;
    private String description;
    private String date;
    private String subject;
    private String grade;
    private String fee;
    private String address;
    private String requirement;
    private String times;
    private int id_parent;

    public Class(int id, String description,String date, String subject, String grade, String fee,
                 String address, String requirement, String times, int id_parent) {
        this.id_class = id;
        this.description = description;
        this.subject = subject;
        this.grade = grade;
        this.fee = fee;
        this.address = address;
        this.requirement = requirement;
        this.times = times;
        this.id_parent = id_parent;
    }

    public Class(String description, String date, String subject, String grade, String fee,
                 String address, String requirement, String times) {
        this.description = description;
        this.date = date;
        this.subject = subject;
        this.grade = grade;
        this.fee = fee;
        this.address = address;
        this.requirement = requirement;
        this.times = times;
    }

    public Class(String description, String requirement, String subject, String fee, String address) {
        this.description = description;
        this.subject = subject;
        this.fee = fee;
        this.address = address;
        this.requirement = requirement;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_parent() {
        return id_parent;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public int getId_class() {
        return id_class;
    }

    public void setId_class(int id_class) {
        this.id_class = id_class;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
