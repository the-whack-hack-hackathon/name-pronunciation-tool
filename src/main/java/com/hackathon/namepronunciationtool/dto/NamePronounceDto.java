package com.hackathon.namepronunciationtool.dto;

public class NamePronounceDto {
    private String name;
    private String gender;

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

    @Override
    public String toString() {
        return "NamePronounceDto{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
