package com.trivia.api.service;

import com.trivia.api.exception.TriviaApiException;
import com.trivia.api.model.AnswerDTO;
import com.trivia.api.model.QuestionDTO;
import com.trivia.api.model.QuestionResultDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    private final OpenTriviaClient openTriviaClient;
    private final Map<Integer, QuestionDTO> questionMap = new TreeMap<>();
    private final Map<Integer, String> correctAnswerMap = new TreeMap<>();

    public QuestionService(OpenTriviaClient openTriviaClient) {
        this.openTriviaClient = openTriviaClient;
    }

    public OpenTriviaWrapperDTO getQuestions(Integer amount) {
        if (amount == null || amount < 1) {
            throw new TriviaApiException("Amount must be greater than 0");
        }
        
        OpenTriviaResponse response = openTriviaClient.fetchQuestions(amount);
        if (response == null) {
            throw new TriviaApiException("Received null response from OpenTriviaDatabase API");
        }

        List<OpenTriviaValue> results = response.getResults();
        if (results == null) {
            throw new TriviaApiException("Received null results from OpenTriviaDatabase API");
        }

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        questionMap.clear();
        correctAnswerMap.clear();

        for (int i = 0; i < results.size(); i++) {
            OpenTriviaValue value = results.get(i);
            String correctAnswer = value.getCorrectAnswer();
            correctAnswerMap.put(i, correctAnswer);

            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(correctAnswer);
            allAnswers.addAll(value.getIncorrectAnswers());
            Collections.shuffle(allAnswers);

            QuestionDTO question = new QuestionDTO(
                    i,
                    value.getType(),
                    value.getDifficulty(),
                    value.getCategory(),
                    value.getQuestion(),
                    allAnswers
            );
            questionMap.put(i, question);
            questionDTOList.add(question);
        }

        OpenTriviaWrapperDTO output = new OpenTriviaWrapperDTO();
        output.setResponse_code(response.getResponseCode());
        output.setResults(questionDTOList);

        return output;
    }

    public Map<Integer, QuestionResultDTO> checkAnswers(List<AnswerDTO> answers) {
        if (answers == null || answers.isEmpty()) {
            throw new TriviaApiException("No answers provided");
        }

        if (questionMap.isEmpty() || correctAnswerMap.isEmpty()) {
            throw new TriviaApiException("No questions available. Please fetch questions first.");
        }

        Map<Integer, QuestionResultDTO> results = new HashMap<>();

        for (AnswerDTO answer : answers) {
            int id = answer.getQuestionId();
            String correct = correctAnswerMap.get(id);
            QuestionDTO question = questionMap.get(id);
            
            if (correct == null || question == null) {
                throw new TriviaApiException("Invalid question ID: " + id + ". This question does not exist.");
            }

            boolean isCorrect = correct.equals(answer.getAnswer());
            results.put(
                    id,
                    new QuestionResultDTO(question.getQuestion(), isCorrect, correct)
            );
        }
        return results;
    }
}
