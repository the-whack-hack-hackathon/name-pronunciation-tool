package com.hackathon.namepronunciationtool.controller;

import com.hackathon.namepronunciationtool.entity.Voice;
import com.hackathon.namepronunciationtool.repo.VoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VoiceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceController.class);

    private final VoiceRepository repo;

    @Autowired
    public VoiceController(VoiceRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/api/voice/list/all")
    public List<Voice> fetchAllVoices() {
        Iterable<Voice> iterator = repo.findAll();
        List<Voice> voiceList = new ArrayList<>();
        iterator.forEach(voiceList::add);
        return voiceList;
    }
}
