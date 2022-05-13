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

    private static final String EN_US_CHRISTOPHER_NEURAL = "en-US-ChristopherNeural";
    private static final String EN_US_JENNY_NEURAL = "en-US-JennyNeural";
    private static final String DEFAULT = "default";
    private static final String REST_END_POINT = "https://eastus.tts.speech.microsoft.com/cognitiveservices/v1";
    private final RestTemplate restTemplate;

    @Autowired
    public NamePronunciationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StreamingResponseBody getVoice(String inputWord) {
        return getVoice(inputWord, null, null);
    }

    public StreamingResponseBody getVoice(NamePronounceDto namePronounceDto) {
        return getVoice(namePronounceDto.getName(), namePronounceDto.getGender(), namePronounceDto.getRate());
    }

    public StreamingResponseBody getVoice(String inputWord, String gender, String rate) {
        HttpHeaders headers = getHttpHeaders();
        if ("m".equalsIgnoreCase(gender)) {
            gender = EN_US_CHRISTOPHER_NEURAL;
        } else {
            gender = EN_US_JENNY_NEURAL;
        }
        if (rate == null) {
            rate = DEFAULT;
        }
        String speechPayload = "<speak version='1.0' xmlns=\"http://www.w3.org/2001/10/synthesis\" xml:lang=\"en-US\"> " +
                "<voice name=\"" + gender + "\">" +
                "<prosody rate=\"" + rate + "\">" + inputWord + "</prosody></voice></speak>";
        LOGGER.info("speechPayload= {}", speechPayload);
        HttpEntity<String> entity = new HttpEntity<>(speechPayload, headers);

        byte[] voice = restTemplate.exchange(REST_END_POINT, HttpMethod.POST, entity, byte[].class).getBody();
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
