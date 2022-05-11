package com.hackathon.namepronunciationtool.web;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;

@RestController
public class NamePronunciationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NamePronunciationController.class);

    @GetMapping(value = "/api/pronounceName/{name}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<StreamingResponseBody> namePronounce(@PathVariable("name") String name) throws Exception {
        LOGGER.info("Pronouncing name = {}", name);
        File file = ResourceUtils.getFile("classpath:static/sounds/service-bell.mp3");
        StreamingResponseBody streamingResponseBody;
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
        streamingResponseBody = outputStream -> IOUtils.copy(fileInputStream, outputStream);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        return new ResponseEntity<>(streamingResponseBody, httpHeaders, HttpStatus.OK);
    }

}
