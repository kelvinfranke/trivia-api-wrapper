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

    public OpenTriviaWrapperDTO fetchQuestions(int amount) {
        String OPEN_TRIVIA_ENDPOINT = "https://opentdb.com/api.php";
        String url = OPEN_TRIVIA_ENDPOINT + "?amount=" + amount;
        OpenTriviaResponse response = restTemplate.getForObject(url, OpenTriviaResponse.class);
        questionsDTO = new TreeMap<>();
        correctAnswers = new TreeMap<>();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        int responseCode = 0;
        if (response != null) {
            responseCode = response.getResponseCode();
            List<OpenTriviaValue> results = response.getResults();

            if (results != null) {
                for (int i = 0; i < results.size(); i++) {
                    OpenTriviaValue value = results.get(i);

                    String correctAnswer = value.getCorrectAnswer();
                    correctAnswers.put(i, new AnswerDTO(i, correctAnswer));

                    List<String> allAnswers = new ArrayList<>();
                    allAnswers.add(correctAnswer);
                    allAnswers.addAll(value.getIncorrectAnswers());
                    Collections.shuffle(allAnswers);
                    QuestionDTO questionDTO = new QuestionDTO(
                            i,
                            value.getType(),
                            value.getDifficulty(),
                            value.getCategory(),
                            value.getQuestion(),
                            allAnswers
                    );
                    questionsDTO.put(i, questionDTO);
                    questionDTOList.add(questionDTO);
                }
            }
        }

        OpenTriviaWrapperDTO output = new OpenTriviaWrapperDTO();
        output.setResponse_code(responseCode);
        output.setResults(questionDTOList);

        return output;
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