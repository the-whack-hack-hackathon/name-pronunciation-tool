package com.hackathon.namepronunciationtool.service;

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
    private final RestTemplate restTemplate;

    @Autowired
    public NamePronunciationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StreamingResponseBody getVoice(String inputWord) {
        return getVoice(inputWord, null);
    }

    public StreamingResponseBody getVoice(NamePronounceDto namePronounceDto) {
        return getVoice(namePronounceDto.getName(), namePronounceDto.getGender());
    }

    public StreamingResponseBody getVoice(String inputWord, String gender) {
        HttpHeaders headers = getHttpHeaders();
        if ("m".equalsIgnoreCase(gender)) {
            gender = "en-US-ChristopherNeural";
        } else {
            gender = "en-US-JennyNeural";
        }
        String body = "<speak version='1.0' xml:lang=\"en-US\"> <voice name=\"" + gender + "\">" + inputWord + "</voice></speak>";
        LOGGER.info("Body= {}", body);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        byte[] voice = restTemplate.exchange("https://eastus.tts.speech.microsoft.com/cognitiveservices/v1", HttpMethod.POST, entity, byte[].class).getBody();
        LOGGER.info("Response received from speech service ");
        assert voice != null;
        InputStream in = new ByteArrayInputStream(voice);
        return outputStream -> IOUtils.copy(in, outputStream);
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
