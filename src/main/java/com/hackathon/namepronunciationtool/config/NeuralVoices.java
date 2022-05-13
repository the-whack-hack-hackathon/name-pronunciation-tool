package com.hackathon.namepronunciationtool.config;

public class NeuralVoices {

    private String voiceName;

    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    @Override
    public String toString() {
        return "NeuralVoices{" +
                " voiceName='" + voiceName + '\'' +
                '}';
    }
}
