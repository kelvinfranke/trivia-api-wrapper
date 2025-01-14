package com.trivia.api.controller;

import com.trivia.api.model.AnswerDTO;
import com.trivia.api.model.QuestionResultDTO;
import com.trivia.api.service.OpenTriviaWrapperDTO;
import com.trivia.api.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public ResponseEntity<OpenTriviaWrapperDTO> getQuestions(@RequestParam(defaultValue = "10") Integer amount) {
        OpenTriviaWrapperDTO response = questionService.getQuestions(amount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<Map<Integer, QuestionResultDTO>> checkAnswers(@RequestBody List<AnswerDTO> answers) {
        Map<Integer, QuestionResultDTO> results = questionService.checkAnswers(answers);
        return ResponseEntity.ok(results);
    }
}
