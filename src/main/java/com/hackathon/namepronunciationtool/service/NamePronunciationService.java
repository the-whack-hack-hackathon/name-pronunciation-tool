package com.hackathon.namepronunciationtool.service;

import com.hackathon.namepronunciationtool.config.LocaleProperties;
import com.hackathon.namepronunciationtool.dto.NamePronounceDto;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class NamePronunciationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NamePronunciationService.class);

    private static final String EN_US_CHRISTOPHER_NEURAL = "en-US-GuyNeural";
    private static final String EN_US_JENNY_NEURAL = "en-US-JennyNeural";
    private static final String REST_END_POINT = "https://eastus.tts.speech.microsoft.com/cognitiveservices/v1";
    private final RestTemplate restTemplate;
    private final LocaleProperties localeProperties;

    @Autowired
    public NamePronunciationService(RestTemplate restTemplate, LocaleProperties localeProperties) {
        this.restTemplate = restTemplate;
        this.localeProperties = localeProperties;
    }

    public StreamingResponseBody getVoice(String inputWord) {
        return getVoice(inputWord, "F", "default", "us");
    }

    public StreamingResponseBody getVoice(NamePronounceDto namePronounceDto) {
        return getVoice(namePronounceDto.getName(), namePronounceDto.getGender(), namePronounceDto.getRate(), namePronounceDto.getLocale());
    }

    public StreamingResponseBody getVoice(String inputWord, String gender, String rate, String locale) {
        HttpHeaders headers = getHttpHeaders();
        String speechPayload = "<speak version='1.0' xmlns=\"http://www.w3.org/2001/10/synthesis\" xml:lang=\"en-US\"> " +
                "<voice name=\"" + getVoiceName(gender, locale) + "\">" +
                "<prosody rate=\"" + rate + "\">" + inputWord + "</prosody></voice></speak>";
        LOGGER.info("speechPayload= {}", speechPayload);
        HttpEntity<String> entity = new HttpEntity<>(speechPayload, headers);

        byte[] voice = restTemplate.exchange(REST_END_POINT, HttpMethod.POST, entity, byte[].class).getBody();
        LOGGER.info("Response received from speech service ");
        assert voice != null;
        InputStream in = new ByteArrayInputStream(voice);
        return outputStream -> IOUtils.copy(in, outputStream);
    }

    private String getVoiceName(String gender, String locale) {
        String key;
        if ("m".equalsIgnoreCase(gender)) {
            key = locale + "Male";
        } else {
            key = locale + "Female";
        }
        LOGGER.info("key = {}", key);
        return localeProperties.getNeuralVoices().get(key).getVoiceName();
    }

    private HttpHeaders getHttpHeaders() {
        String jwt = getJWT();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/ssml+xml");
        headers.set("X-Microsoft-OutputFormat", "audio-16khz-32kbitrate-mono-mp3");
        headers.set("Authorization", "Bearer " + jwt);
        headers.set("Ocp-Apim-Subscription-Key", "091137369163400ea3344963ab427d91");
        return headers;
    }

    private String getJWT() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/jwt");
        headers.set("Ocp-Apim-Subscription-Key", "091137369163400ea3344963ab427d91");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String jwt = restTemplate.exchange("https://eastus.api.cognitive.microsoft.com/sts/v1.0/issuetoken", HttpMethod.POST, entity, String.class).getBody();
        LOGGER.info("jwt = {}", jwt);
        return jwt;
    }
}
