package com.trivia.api.service;

import java.util.List;

public class OpenTriviaResponse {
    private List<OpenTriviaValue> results;

    public List<OpenTriviaValue> getResults() {
        return results;
    }

    public void setResults(List<OpenTriviaValue> results) {
        this.results = results;
    }
}
