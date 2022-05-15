package com.hackathon.namepronunciationtool.controller;

import com.hackathon.namepronunciationtool.entity.Voice;
import com.hackathon.namepronunciationtool.repo.VoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VoiceController.class)
class VoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoiceRepository mockRepo;

    @Test
    void testFetchAllVoices() throws Exception {
        // Setup
        // Configure VoiceRepository.findAll(...).
        final Voice voice = new Voice();
        voice.setId(0);
        voice.setLocale("locale");
        voice.setGender("gender");
        voice.setName("name");
        final Iterable<Voice> voices = List.of(voice);
        when(mockRepo.findAll()).thenReturn(voices);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/voice/list/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());
    }

    @Test
    void testFetchAllVoices_VoiceRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/voice/list/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
