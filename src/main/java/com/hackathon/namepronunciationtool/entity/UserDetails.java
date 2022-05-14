package com.hackathon.namepronunciationtool.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserDetails implements Persistable<String> {

    @Id
    private String uid;
    private String firstName;
    private String lastName;
    private String preferredFirstName;
    private String preferredLastName;
    private String email;
    private String userPronunciation;
    private String systemPronunciation;

    private int voiceId;

    @Transient
    private Boolean isInsert = true;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredFirstName() {
        return preferredFirstName;
    }

    public void setPreferredFirstName(String preferredFirstName) {
        this.preferredFirstName = preferredFirstName;
    }

    public String getPreferredLastName() {
        return preferredLastName;
    }

    public void setPreferredLastName(String preferredLastName) {
        this.preferredLastName = preferredLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPronunciation() {
        return userPronunciation;
    }

    public void setUserPronunciation(String userPronunciation) {
        this.userPronunciation = userPronunciation;
    }

    public String getSystemPronunciation() {
        return systemPronunciation;
    }

    public void setSystemPronunciation(String systemPronunciation) {
        this.systemPronunciation = systemPronunciation;
    }

    public Boolean getInsert() {
        return isInsert;
    }

    public void setInsert(Boolean insert) {
        isInsert = insert;
    }

    @Override
    public String getId() {
        return uid;
    }

    @Override
    public boolean isNew() {
        return isInsert;
    }

    public int getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(int voiceId) {
        this.voiceId = voiceId;
    }
}
