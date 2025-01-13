package com.trivia.api.service;

import com.trivia.api.model.AnswerDTO;
import com.trivia.api.model.QuestionResultDTO;
import org.springframework.stereotype.Service;
import com.trivia.api.model.QuestionDTO;

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
        OpenTriviaResponse response = openTriviaClient.fetchQuestions(amount);

        List<OpenTriviaValue> results = response.getResults();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

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
        Map<Integer, QuestionResultDTO> results = new HashMap<>();

        for (AnswerDTO answer : answers) {
            int id = answer.getQuestionId();
            String correct = correctAnswerMap.get(id);
            boolean isCorrect = correct.equals(answer.getAnswer());

            QuestionDTO question = questionMap.get(id);
            results.put(
                    id,
                    new QuestionResultDTO(question.getQuestion(), isCorrect, correct)
            );
        }
        return results;
    }
}

