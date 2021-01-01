package com.jz_jec_g01.tripwiz.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int userId;
    private String name;
    private String age;
    private int gender;
    private String job;
    private String nationality;
    private String introduction;
    private int guideStatus;
    private String area;
    private String week;
    private String profile;
    private String use_languages;
    private double rating;


    public User() {

    }

    public User(int userId, String name, String age, int gender, String job, String nationality, String introduction, int guideStatus, String area, String week, String profile, String use_languages, double rating) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.job = job;
        this.nationality = nationality;
        this.introduction = introduction;
        this.guideStatus = guideStatus;
        this.area = area ;
        this.week = week;
        this.profile = profile;
        this.use_languages = use_languages;
        this.rating = rating;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getAge() { return age; }
    public int getGender() { return gender; }
    public String getJob() { return job; }
    public String getNationality() { return nationality; }
    public String getIntroduction() { return introduction; }
    public int getGuideStatus() { return guideStatus; }
    public String getProfile() {return profile; }
    public double getRating() {return rating; }
    public String getArea() { return area; }
    public String getWeek() { return week; }
    public String getUse_languages() { return use_languages; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setAge(String age) { this.age = age; }
    public void setGender(int gender) { this.gender = gender; }
    public void setJob(String job) { this.job = job; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
    public void setGuideStatus(int guideStatus) { this.guideStatus = guideStatus; }
    public void setProfile(String profile) {this.profile = profile; }
    public void setRating(double rating) {this.rating = rating; }
    public void setArea(String area) { this.area = area; }
    public void setWeek(String week) { this.week = week; }

    public void setUse_languages(String use_languages) { this.use_languages = use_languages; }
}
