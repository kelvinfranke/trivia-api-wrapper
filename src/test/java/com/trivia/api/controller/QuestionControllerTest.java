package com.trivia.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trivia.api.model.AnswerDTO;
import com.trivia.api.model.QuestionResultDTO;
import com.trivia.api.service.OpenTriviaWrapperDTO;
import com.trivia.api.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        when(questionService.getQuestions(anyInt())).thenReturn(new OpenTriviaWrapperDTO());
        
        Map<Integer, QuestionResultDTO> defaultResults = new HashMap<>();
        defaultResults.put(1, new QuestionResultDTO("Test question", true, "correct answer"));
        when(questionService.checkAnswers(any())).thenReturn(defaultResults);
    }

    @Test
    void getQuestions_ShouldReturnQuestions() throws Exception {
        mockMvc.perform(get("/questions")
                .param("amount", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void checkAnswers_ShouldReturnResults() throws Exception {
        List<AnswerDTO> answers = new ArrayList<>();
        answers.add(new AnswerDTO(1, "test answer"));
        
        mockMvc.perform(post("/checkanswers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(answers)))
                .andExpect(status().isOk());
    }
}
