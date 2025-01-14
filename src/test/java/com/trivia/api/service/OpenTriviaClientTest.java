package com.trivia.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenTriviaClientTest {

    @Mock
    private RestTemplate restTemplate;

    private OpenTriviaClient openTriviaClient;

    @BeforeEach
    void setUp() {
        openTriviaClient = new OpenTriviaClient();
        ReflectionTestUtils.setField(openTriviaClient, "restTemplate", restTemplate);
        
        OpenTriviaResponse mockResponse = new OpenTriviaResponse();
        mockResponse.setResponseCode(0);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(mockResponse);
    }

    @Test
    void fetchQuestions_ShouldReturnResponse() {
        OpenTriviaResponse result = openTriviaClient.fetchQuestions(5);

        assertThat(result).isNotNull();
        assertThat(result.getResponseCode()).isZero();
    }

    @Test
    void fetchQuestions_ShouldConstructCorrectUrl() {
        int amount = 10;
        String expectedUrl = "https://opentdb.com/api.php?amount=" + amount;
        when(restTemplate.getForObject(expectedUrl, OpenTriviaResponse.class))
            .thenReturn(new OpenTriviaResponse());

        OpenTriviaResponse result = openTriviaClient.fetchQuestions(amount);

        assertThat(result).isNotNull();
    }
}
