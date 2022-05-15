package com.hackathon.namepronunciationtool.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hackathon.namepronunciationtool.entity.UserDetails;
import com.hackathon.namepronunciationtool.repo.UserRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository mockUserRepository;

    @Test
    void testCreateUser() throws Exception {

        final UserDetails userDetails = new UserDetails();
        userDetails.setUid("uid");
        userDetails.setFirstName("firstName");
        userDetails.setLastName("lastName");
        userDetails.setPreferredFirstName("preferredFirstName");
        userDetails.setPreferredLastName("preferredLastName");
        userDetails.setEmail("email");
        userDetails.setUserPronunciation("userPronunciation");
        userDetails.setSystemPronunciation("systemPronunciation");
        userDetails.setInsert(false);
        userDetails.setVoiceId(0);
        when(mockUserRepository.save(any(UserDetails.class))).thenReturn(userDetails);

        final MockHttpServletResponse response = mockMvc.perform(post("/api/users")
                        .content(toJson(userDetails)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("SUCCESS");
        verify(mockUserRepository).save(any(UserDetails.class));
    }

    @Test
    void testUpdateUser() throws Exception {

        final UserDetails userDetails = new UserDetails();
        userDetails.setUid("uid");
        userDetails.setFirstName("firstName");
        userDetails.setLastName("lastName");
        userDetails.setPreferredFirstName("preferredFirstName");
        userDetails.setPreferredLastName("preferredLastName");
        userDetails.setEmail("email");
        userDetails.setUserPronunciation("userPronunciation");
        userDetails.setSystemPronunciation("systemPronunciation");
        userDetails.setInsert(false);
        userDetails.setVoiceId(0);
        when(mockUserRepository.findByUid("uid")).thenReturn(userDetails);

        final UserDetails userDetails1 = new UserDetails();
        userDetails1.setUid("uid");
        userDetails1.setFirstName("firstName");
        userDetails1.setLastName("lastName");
        userDetails1.setPreferredFirstName("preferredFirstName");
        userDetails1.setPreferredLastName("preferredLastName");
        userDetails1.setEmail("email");
        userDetails1.setUserPronunciation("userPronunciation");
        userDetails1.setSystemPronunciation("systemPronunciation");
        userDetails1.setInsert(false);
        userDetails1.setVoiceId(0);
        when(mockUserRepository.save(any(UserDetails.class))).thenReturn(userDetails1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/users/{uid}", "uid")
                        .content(toJson(userDetails1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("SUCCESS");
        verify(mockUserRepository).save(any(UserDetails.class));
    }

    @Test
    void testDeleteUser() throws Exception {

        when(mockUserRepository.deleteByUid("uid")).thenReturn(0L);

        final MockHttpServletResponse response = mockMvc.perform(delete("/api/users/{uid}", "uid")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("SUCCESS");
    }

    @Test
    void testFetchUser() throws Exception {

        final UserDetails userDetails1 = new UserDetails();
        userDetails1.setUid("uid");
        userDetails1.setFirstName("firstName");
        userDetails1.setLastName("lastName");
        userDetails1.setPreferredFirstName("preferredFirstName");
        userDetails1.setPreferredLastName("preferredLastName");
        userDetails1.setEmail("email");
        userDetails1.setUserPronunciation("userPronunciation");
        userDetails1.setSystemPronunciation("systemPronunciation");
        userDetails1.setInsert(false);
        userDetails1.setVoiceId(0);
        final Optional<UserDetails> userDetails = Optional.of(userDetails1);
        when(mockUserRepository.findById("uid")).thenReturn(userDetails);

        final MockHttpServletResponse response = mockMvc.perform(get("/api/users/{uid}", "uid")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String expectedResult = "{\"uid\":\"uid\",\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"preferredFirstName\":\"preferredFirstName\",\"preferredLastName\":\"preferredLastName\",\"email\":\"email\",\"userPronunciation\":\"userPronunciation\",\"systemPronunciation\":\"systemPronunciation\",\"voiceId\":0,\"id\":\"uid\",\"insert\":false,\"new\":false}";

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedResult);
    }

    @Test
    void testFetchUser_UserRepositoryReturnsAbsent() throws Exception {

        when(mockUserRepository.findById("uid")).thenReturn(Optional.empty());

        final MockHttpServletResponse response = mockMvc.perform(get("/api/users/{uid}", "uid")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    void testFetchAllUsers() throws Exception {

        final UserDetails userDetails1 = new UserDetails();
        userDetails1.setUid("uid");
        userDetails1.setFirstName("firstName");
        userDetails1.setLastName("lastName");
        userDetails1.setPreferredFirstName("preferredFirstName");
        userDetails1.setPreferredLastName("preferredLastName");
        userDetails1.setEmail("email");
        userDetails1.setUserPronunciation("userPronunciation");
        userDetails1.setSystemPronunciation("systemPronunciation");
        userDetails1.setInsert(false);
        userDetails1.setVoiceId(0);
        final Iterable<UserDetails> userDetails = List.of(userDetails1);
        when(mockUserRepository.findAll()).thenReturn(userDetails);
        final MockHttpServletResponse response = mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        String expectedResult = "[{\"uid\":\"uid\",\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"preferredFirstName\":\"preferredFirstName\",\"preferredLastName\":\"preferredLastName\",\"email\":\"email\",\"userPronunciation\":\"userPronunciation\",\"systemPronunciation\":\"systemPronunciation\",\"voiceId\":0,\"id\":\"uid\",\"insert\":false,\"new\":false}]";

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedResult);
    }

    @Test
    void testFetchAllUsers_UserRepositoryReturnsNoItems() throws Exception {
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());
        final MockHttpServletResponse response = mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    private String toJson(UserDetails userDetails) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            return ow.writeValueAsString(userDetails );
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}


