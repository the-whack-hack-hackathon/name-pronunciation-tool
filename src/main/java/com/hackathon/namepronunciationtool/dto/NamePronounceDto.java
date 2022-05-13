package com.hackathon.namepronunciationtool.dto;

public class NamePronounceDto {
    private String name;
    private String gender = "F";
    private String rate = "default";
    private String locale = "us";

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "NamePronounceDto{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", rate='" + rate + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
