package com.hackathon.namepronunciationtool.service;

import com.hackathon.namepronunciationtool.config.LocaleProperties;
import com.hackathon.namepronunciationtool.config.NeuralVoices;
import com.hackathon.namepronunciationtool.config.SpeechConfigProperties;
import com.hackathon.namepronunciationtool.dto.NamePronounceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NamePronunciationServiceTest {

    @Mock
    private RestTemplate mockRestTemplate;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private LocaleProperties mockLocaleProperties;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private SpeechConfigProperties mockSpeechConfigProperties;

    private NamePronunciationService namePronunciationServiceUnderTest;

    @BeforeEach
    void setUp() {
        namePronunciationServiceUnderTest = new NamePronunciationService(mockRestTemplate, mockLocaleProperties,
                mockSpeechConfigProperties);
        ResponseEntity<String> response = new ResponseEntity<String>("jwtToken", HttpStatus.ACCEPTED);

        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any())
        ).thenReturn(response);



    }

    @Test
    void testGetVoice1() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class))).thenReturn(new ResponseEntity<>("body", HttpStatus.OK));
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        // Configure LocaleProperties.getNeuralVoices(...).
        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord");

        // Verify the results
    }

    @Test
    void testGetVoice1_RestTemplateThrowsRestClientException() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class)))
                .thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        // Configure LocaleProperties.getNeuralVoices(...).
        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord");

        // Verify the results
    }

    @Test
    void testGetVoice2() {
        // Setup
        final NamePronounceDto namePronounceDto = new NamePronounceDto();
        namePronounceDto.setName("name");
        namePronounceDto.setGender("gender");
        namePronounceDto.setRate("rate");
        namePronounceDto.setLocale("locale");

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class))).thenReturn(new ResponseEntity<>("body", HttpStatus.OK));
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        // Configure LocaleProperties.getNeuralVoices(...).
        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice(namePronounceDto);

        // Verify the results
    }

    @Test
    void testGetVoice2_RestTemplateThrowsRestClientException() {
        // Setup
        final NamePronounceDto namePronounceDto = new NamePronounceDto();
        namePronounceDto.setName("name");
        namePronounceDto.setGender("gender");
        namePronounceDto.setRate("rate");
        namePronounceDto.setLocale("locale");

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class)))
                .thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        // Configure LocaleProperties.getNeuralVoices(...).
        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice(namePronounceDto);

        // Verify the results
    }

    @Test
    void testGetVoice3() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class))).thenReturn(new ResponseEntity<>("body", HttpStatus.OK));
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        // Configure LocaleProperties.getNeuralVoices(...).
        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord", "gender", "rate",
                "locale");

        // Verify the results
    }

    @Test
    void testGetVoice3_RestTemplateThrowsRestClientException() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class)))
                .thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        // Configure LocaleProperties.getNeuralVoices(...).
        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord", "gender", "rate",
                "locale");

        // Verify the results
    }

    @Test
    void testGetVoice4() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class))).thenReturn(new ResponseEntity<>("body", HttpStatus.OK));
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");
        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord", "name", "rate");

        // Verify the results
    }

    @Test
    void testGetVoice4_RestTemplateThrowsRestClientException() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(eq("tokenUrl"), eq(HttpMethod.GET), eq(new HttpEntity<>(null)), eq(String.class),
                any(Object.class)))
                .thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");
        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord", "name", "rate");

        // Verify the results
    }
}
