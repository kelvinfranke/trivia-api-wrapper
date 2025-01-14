package com.trivia.api.service;

import com.trivia.api.exception.TriviaApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenTriviaClient {
    private final RestTemplate restTemplate;

    public OpenTriviaClient() {
        this.restTemplate = new RestTemplate();
    }

    public OpenTriviaResponse fetchQuestions(int amount) {
        try {
            String endpoint = "https://opentdb.com/api.php?amount=" + amount;
            OpenTriviaResponse response = restTemplate.getForObject(endpoint, OpenTriviaResponse.class);
            
            if (response == null) {
                throw new TriviaApiException("Received null response from OpenTriviaDatabase API");
            }
            
            if (response.getResponseCode() != 0) {
                throw new TriviaApiException("Error from OpenTriviaDatabase API: code " + response.getResponseCode());
            }
            
            return response;
        } catch (Exception e) {
            if (e instanceof TriviaApiException) {
                throw e;
            }
            throw new TriviaApiException("Failed to fetch questions from OpenTriviaDatabase API", e);
        }
    }
}
