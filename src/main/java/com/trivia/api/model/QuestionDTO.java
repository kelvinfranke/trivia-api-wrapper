package com.trivia.api.model;

import java.util.List;

public class QuestionDTO {
    private int questionId;
    private String type;
    private String difficulty;
    private String category;
    private String question;
    private List<String> answers;

    public QuestionDTO(int questionId, String type, String difficulty, String category, String question, List<String> answers) {
        this.questionId = questionId;
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.answers = answers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
