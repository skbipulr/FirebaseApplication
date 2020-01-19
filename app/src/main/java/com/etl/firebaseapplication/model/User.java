package com.etl.firebaseapplication.model;

public class User {
    private String name;
    private String gender;
    private String age;
    private String userId;

    public User() {
    }

    public User(String name, String gender, String age, String userId) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.userId = userId;
    }

    public User(String name, String gender, String age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
