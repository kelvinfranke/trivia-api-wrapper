package com.trivia.api.model;

//Question:
//1. type
//2. difficulty
//3. category
//4. question
//5. correct_answer
//6. incorrect_answers

public class Question {
    private int id;
    private String question_text;
    private String correct_answer;
    private String[] incorrect_answers;

    public Question(int id, String question_text, String correct_answer, String[] incorrect_answers) {
        this.id = id;
        this.question_text = question_text;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(String[] incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }
}
