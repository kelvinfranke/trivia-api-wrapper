package com.trivia.api.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenTriviaResponse {

    @JsonProperty("response_code")
    private int responseCode;
    private List<OpenTriviaValue> results;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<OpenTriviaValue> getResults() {
        return results;
    }

    public void setResults(List<OpenTriviaValue> results) {
        this.results = results;
    }
}
