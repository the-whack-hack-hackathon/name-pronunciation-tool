package com.hackathon.namepronunciationtool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("locale-properties")
public class LocaleProperties {
    private Map<String, NeuralVoices> neuralVoices;

    public Map<String, NeuralVoices> getNeuralVoices() {
        return neuralVoices;
    }

    public void setNeuralVoices(Map<String, NeuralVoices> neuralVoices) {
        this.neuralVoices = neuralVoices;
    }

    @Override
    public String toString() {
        return "LocaleProperties{" +
                "neuralVoices=" + neuralVoices +
                '}';
    }
}
