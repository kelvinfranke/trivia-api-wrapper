package com.trivia.api.model;

public class QuestionResultDTO {
    private String question;
    private Boolean isCorrect;
    private String correctAnswer;

    public QuestionResultDTO(String question, Boolean isCorrect, String correctAnswer) {
        this.question = question;
        this.isCorrect = isCorrect;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
