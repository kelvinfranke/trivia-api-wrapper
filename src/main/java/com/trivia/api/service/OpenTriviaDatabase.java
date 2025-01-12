package com.trivia.api.service;

import com.trivia.api.model.AnswerDTO;
import com.trivia.api.model.QuestionDTO;
import com.trivia.api.model.QuestionResultDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenTriviaDatabase {
    private final RestTemplate restTemplate;
    private Map<Integer, QuestionDTO> questionsDTO;
    private Map<Integer, AnswerDTO> correctAnswers;

    public OpenTriviaDatabase() {
        this.restTemplate = new RestTemplate();
    }

    // TODO: misschien doet deze class teveel en moet je enkel de questions ophalen, en dan in service de echte logica van DTO's aanmaken?
    public Map<Integer, QuestionDTO> fetchQuestions(int amount){
        String OPEN_TRIVIA_ENDPOINT = "https://opentdb.com/api.php";
        String url = OPEN_TRIVIA_ENDPOINT + "?amount=" + amount;
        OpenTriviaResponse response = restTemplate.getForObject(url, OpenTriviaResponse.class);

        questionsDTO = new TreeMap<>();
        correctAnswers = new TreeMap<>();
        if (response != null) {
            List<OpenTriviaValue> results = response.getResults();
            if (results != null) {
                for (int i = 0; i < results.size(); i++) {
                    OpenTriviaValue value = results.get(i);
                    String correctAnswer = value.getCorrectAnswer();
                    AnswerDTO correctAnswerMapping = new AnswerDTO(i, correctAnswer);
                    correctAnswers.put(i, correctAnswerMapping);

                    List<String> allAnswers = new ArrayList<>();
                    allAnswers.add(correctAnswer);
                    allAnswers.addAll(value.getIncorrectAnswers());
                    Collections.shuffle(allAnswers);
                    QuestionDTO questionDTO = new QuestionDTO(
                            i,
                            value.getQuestion(),
                            allAnswers
                    );
                    questionsDTO.put(i, questionDTO);
                }
            }
        }
        return questionsDTO;
    }

    public Map<Integer, QuestionResultDTO> checkAnswers(List<AnswerDTO> answers) {
        Map<Integer, QuestionResultDTO> results = new HashMap<>();

        for (AnswerDTO answer : answers) {
            int id = answer.getQuestionId();
            String correctAnswer = this.correctAnswers.get(id).getAnswer();
            boolean isCorrect = correctAnswer.equals(answer.getAnswer());
            results.put(
                    answer.getQuestionId(),
                    new QuestionResultDTO(this.questionsDTO.get(id).getQuestion(), isCorrect, correctAnswer)
            );
        }
        return results;
    }
}