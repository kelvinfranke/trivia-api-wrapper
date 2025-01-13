package com.trivia.api.controller;

import com.trivia.api.model.AnswerDTO;
import com.trivia.api.model.QuestionResultDTO;
import com.trivia.api.service.OpenTriviaWrapperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.trivia.api.service.QuestionService;

import java.util.List;
import java.util.Map;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public OpenTriviaWrapperDTO getQuestions(@RequestParam Integer amount){
        return questionService.getQuestions(amount);
    }

    @PostMapping("/checkanswers")
    public Map<Integer, QuestionResultDTO> checkAnswers(@RequestBody List<AnswerDTO> answers){
        return questionService.checkAnswers(answers);
    }

}
