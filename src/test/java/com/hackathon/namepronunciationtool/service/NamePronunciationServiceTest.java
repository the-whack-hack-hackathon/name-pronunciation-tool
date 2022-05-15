package com.hackathon.namepronunciationtool.service;

import com.hackathon.namepronunciationtool.config.LocaleProperties;
import com.hackathon.namepronunciationtool.config.NeuralVoices;
import com.hackathon.namepronunciationtool.config.SpeechConfigProperties;
import com.hackathon.namepronunciationtool.dto.NamePronounceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    }

    @Test
    void testGetVoice1() {

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>("jwtToken".getBytes(), HttpStatus.ACCEPTED);
        ResponseEntity<String> response2 = new ResponseEntity<String>("jwtToken", HttpStatus.ACCEPTED);
        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenReturn(response2).thenReturn(response);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("usFemale", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        // Run the test
        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord");
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetVoice1_RestTemplateThrowsRestClientException() {

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");

        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        assertThrows(RestClientException.class, () -> namePronunciationServiceUnderTest.getVoice("inputWord"));
    }
    @Test
    void testGetVoice2() {
        final NamePronounceDto namePronounceDto = new NamePronounceDto();
        namePronounceDto.setName("name");
        namePronounceDto.setGender("gender");
        namePronounceDto.setRate("rate");
        namePronounceDto.setLocale("locale");

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>("jwtToken".getBytes(), HttpStatus.ACCEPTED);
        ResponseEntity<String> response2 = new ResponseEntity<String>("jwtToken", HttpStatus.ACCEPTED);
        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenReturn(response2).thenReturn(response);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("localeFemale", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice(namePronounceDto);
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetVoice2_RestTemplateThrowsRestClientException() {

        final NamePronounceDto namePronounceDto = new NamePronounceDto();
        namePronounceDto.setName("name");
        namePronounceDto.setGender("gender");
        namePronounceDto.setRate("rate");
        namePronounceDto.setLocale("locale");

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
       when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");

        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("localeFemale", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

       assertThrows(RestClientException.class, () -> namePronunciationServiceUnderTest.getVoice(namePronounceDto));

    }

    @Test
    void testGetVoice3() {

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>("jwtToken".getBytes(), HttpStatus.ACCEPTED);
        ResponseEntity<String> response2 = new ResponseEntity<String>("jwtToken", HttpStatus.ACCEPTED);
        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenReturn(response2).thenReturn(response);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");

        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("localeFemale", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);

        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord", "gender", "rate",
                "locale");
       assertNotNull(result);
    }

    @Test
    void testGetVoice3_RestTemplateThrowsRestClientException() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");

        final NeuralVoices neuralVoices = new NeuralVoices();
        neuralVoices.setVoiceName("voiceName");
        final Map<String, NeuralVoices> stringNeuralVoicesMap = Map.ofEntries(Map.entry("value", neuralVoices));
        when(mockLocaleProperties.getNeuralVoices()).thenReturn(stringNeuralVoicesMap);


        assertThrows(RestClientException.class, () ->  namePronunciationServiceUnderTest.getVoice("inputWord", "gender", "rate",
                "locale"));
    }

    @Test
    void testGetVoice4() {
        // Setup
        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>("jwtToken".getBytes(), HttpStatus.ACCEPTED);
        ResponseEntity<String> response2 = new ResponseEntity<String>("jwtToken", HttpStatus.ACCEPTED);
        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenReturn(response2).thenReturn(response);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");
        when(mockSpeechConfigProperties.getOutputFormat()).thenReturn("result");
        when(mockSpeechConfigProperties.getServiceUrl()).thenReturn("result");

        final StreamingResponseBody result = namePronunciationServiceUnderTest.getVoice("inputWord", "name", "rate");
        assertNotNull(result);
    }

    @Test
    void testGetVoice4_RestTemplateThrowsRestClientException() {

        when(mockSpeechConfigProperties.getSubscriptionKey()).thenReturn("result");
        when(mockRestTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class))
        ).thenThrow(RestClientException.class);
        when(mockSpeechConfigProperties.getTokenUrl()).thenReturn("tokenUrl");

        assertThrows(RestClientException.class, () ->  namePronunciationServiceUnderTest.getVoice("inputWord", "name", "rate"));

    }
}
