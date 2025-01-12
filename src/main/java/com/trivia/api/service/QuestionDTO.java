package com.trivia.api.service;

import java.util.List;

public class QuestionDTO {
    private int questionId;
    private String question;
    private List<String> answers;

    public QuestionDTO(int questionId, String question, List<String> answers) {
        this.questionId = questionId;
        this.question = question;
        this.answers = answers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
