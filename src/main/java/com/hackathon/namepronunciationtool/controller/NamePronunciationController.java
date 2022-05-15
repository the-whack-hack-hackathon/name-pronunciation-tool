package com.hackathon.namepronunciationtool.controller;

import com.hackathon.namepronunciationtool.dto.NamePronounceDto;
import com.hackathon.namepronunciationtool.entity.Voice;
import com.hackathon.namepronunciationtool.repo.VoiceRepository;
import com.hackathon.namepronunciationtool.service.NamePronunciationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Optional;

@RestController
public class NamePronunciationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NamePronunciationController.class);

    private final NamePronunciationService namePronunciationService;

    private final VoiceRepository voiceRepository;

    @Autowired
    public NamePronunciationController(NamePronunciationService namePronunciationService, VoiceRepository voiceRepository) {
        this.namePronunciationService = namePronunciationService;
        this.voiceRepository = voiceRepository;
    }

    @GetMapping(value = "/api/pronounceName/{name}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<StreamingResponseBody> namePronounce(@PathVariable("name") String name) {
        LOGGER.info("Pronouncing name = {}", name);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        StreamingResponseBody streamingResponseBody = namePronunciationService.getVoice(name);
        LOGGER.info("Success");
        return new ResponseEntity<>(streamingResponseBody, httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/api/pronounceNameWithVoice/{voiceId}/{name}/{rate}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<StreamingResponseBody> namePronounceWithVoice(@PathVariable("voiceId") int voiceId, @PathVariable("name") String name, @PathVariable("rate") String rate) {
        LOGGER.info("Pronouncing voiceId = {}, name = {}, rate={}", voiceId, name, rate);
        HttpHeaders httpHeaders = new HttpHeaders();
        Optional<Voice> voice = voiceRepository.findById(voiceId);
        if (voice.isPresent()) {
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
            StreamingResponseBody streamingResponseBody = namePronunciationService.getVoice(name, voice.get().getName(), rate);
            LOGGER.info("Success");
            return new ResponseEntity<>(streamingResponseBody, httpHeaders, HttpStatus.OK);
        } else {
            LOGGER.error("Voice not found: {}", voiceId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/api/pronounceName", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<StreamingResponseBody> namePronounce(@RequestBody NamePronounceDto namePronounceDto) {
        LOGGER.info("Pronouncing name = {}", namePronounceDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        StreamingResponseBody streamingResponseBody = namePronunciationService.getVoice(namePronounceDto);
        LOGGER.info("Success");
        return new ResponseEntity<>(streamingResponseBody, httpHeaders, HttpStatus.OK);
    }


}
