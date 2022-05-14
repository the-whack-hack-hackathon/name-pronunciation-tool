package com.hackathon.namepronunciationtool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("speech-config-properties")
public class SpeechConfigProperties {
    private String subscriptionKey;
    private String outputFormat;
    private String serviceUrl;
    private String tokenUrl;

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    @Override
    public String toString() {
        return "SpeechConfigProperties{" +
                "subscriptionKey='" + subscriptionKey + '\'' +
                ", outputFormat='" + outputFormat + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                ", tokenUrl='" + tokenUrl + '\'' +
                '}';
    }
}
