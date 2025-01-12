package com.trivia.api.service;

import com.trivia.api.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    private final OpenTriviaDatabase openTriviaDatabase;

    public QuestionService() {
        openTriviaDatabase = new OpenTriviaDatabase();
    }

//    public List<Question> getQuestions(Integer amount) {
    public Map<Integer, QuestionDTO> getQuestions(Integer amount) {
        return openTriviaDatabase.fetchQuestions(amount);
    }

    public Map<Integer, QuestionResultDTO>  checkAnswers(List<AnswerDTO> answers){
        return openTriviaDatabase.checkAnswers(answers);
    }
}
