package com.hackathon.namepronunciationtool.web;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
public class NamePronunciationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NamePronunciationController.class);
    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/api/pronounceName/{name}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<StreamingResponseBody> namePronounce(@PathVariable("name") String name) {
        LOGGER.info("Pronouncing name = {}", name);
     //   File file = ResourceUtils.getFile("classpath:static/sounds/service-bell.mp3");
       // StreamingResponseBody streamingResponseBody;
       // FileInputStream fileInputStream = FileUtils.openInputStream(file);
        //streamingResponseBody = outputStream -> IOUtils.copy(fileInputStream, outputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");

        String jwt = getJWT();
        StreamingResponseBody streamingResponseBody = getVoice(jwt, name);
        LOGGER.info("Success");
        return new ResponseEntity<>(streamingResponseBody, httpHeaders, HttpStatus.OK);
    }

    private StreamingResponseBody getVoice(String jwt, String inputWord) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/ssml+xml");
        headers.set("X-Microsoft-OutputFormat", "audio-16khz-32kbitrate-mono-mp3");
        headers.set("Authorization","Bearer " + jwt);
        headers.set("Ocp-Apim-Subscription-Key", "091137369163400ea3344963ab427d91");

        String body =   "<speak version='1.0' xml:lang='en-US'>  <voice xml:lang='en-US' xml:gender='Male'> %s </voice> </speak>";
        body = String.format(body, inputWord);
        LOGGER.info("Body= {}", body);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        byte[] voice = restTemplate.exchange("https://eastus.tts.speech.microsoft.com/cognitiveservices/v1", HttpMethod.POST, entity, byte[].class).getBody();
        LOGGER.info("Response received from speech service ");
        assert voice != null;
        InputStream in = new ByteArrayInputStream(voice);
        return outputStream -> IOUtils.copy(in, outputStream);
    }


    private String getJWT() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/jwt");
        headers.set("Ocp-Apim-Subscription-Key", "091137369163400ea3344963ab427d91");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String jwt = restTemplate.exchange("https://eastus.api.cognitive.microsoft.com/sts/v1.0/issuetoken", HttpMethod.POST, entity, String.class).getBody();
        LOGGER.info("jwt = {}", jwt);
        return jwt;
    }
}
