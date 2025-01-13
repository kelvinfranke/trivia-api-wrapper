package com.trivia.api.service;

import com.trivia.api.model.QuestionDTO;

import java.util.List;

public class OpenTriviaWrapperDTO {
    private int response_code;
    private List<QuestionDTO> results;

    public OpenTriviaWrapperDTO() {}

    public OpenTriviaWrapperDTO(int response_code, List<QuestionDTO> results) {
        this.response_code = response_code;
        this.results = results;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<QuestionDTO> getResults() {
        return results;
    }

    public void setResults(List<QuestionDTO> results) {
        this.results = results;
    }
}
