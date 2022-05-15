package com.hackathon.namepronunciationtool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hackathon.namepronunciationtool.dto.NamePronounceDto;
import com.hackathon.namepronunciationtool.entity.Voice;
import com.hackathon.namepronunciationtool.repo.VoiceRepository;
import com.hackathon.namepronunciationtool.service.NamePronunciationService;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NamePronunciationController.class)
class NamePronunciationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NamePronunciationService mockNamePronunciationService;
    @MockBean
    private VoiceRepository mockVoiceRepository;

    @Test
    void testNamePronounce1() throws Exception {
        // Setup
        when(mockNamePronunciationService.getVoice("name")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/pronounceName/{name}", "name")
                        .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testNamePronounceWithVoice() throws Exception {
        // Setup
        // Configure VoiceRepository.findById(...).
        final Voice voice1 = new Voice();
        voice1.setId(0);
        voice1.setLocale("locale");
        voice1.setGender("gender");
        voice1.setName("name");
        final Optional<Voice> voice = Optional.of(voice1);
        when(mockVoiceRepository.findById(0)).thenReturn(voice);

        when(mockNamePronunciationService.getVoice("name", "name", "rate")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/pronounceNameWithVoice/{voiceId}/{name}/{rate}", 0, "name", "rate")
                                .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testNamePronounceWithVoice_VoiceRepositoryReturnsAbsent() throws Exception {
        // Setup
        when(mockVoiceRepository.findById(0)).thenReturn(Optional.empty());
        when(mockNamePronunciationService.getVoice("name", "name", "rate")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/pronounceNameWithVoice/{voiceId}/{name}/{rate}", 0, "name", "rate")
                                .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void testNamePronounce2() throws Exception {
        // Setup
        when(mockNamePronunciationService.getVoice(any(NamePronounceDto.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/pronounceName")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testNamePronounce3() throws Exception {
        // Setup
        when(mockNamePronunciationService.getVoice(any(NamePronounceDto.class))).thenReturn(null);
        NamePronounceDto namePronounceDto = new NamePronounceDto();
        namePronounceDto.setName("testname");
        namePronounceDto.setGender("male");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(namePronounceDto );
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/pronounceName")
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


}
