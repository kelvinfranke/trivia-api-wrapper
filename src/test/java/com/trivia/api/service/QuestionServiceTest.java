package com.trivia.api.service;

import com.trivia.api.exception.TriviaApiException;
import com.trivia.api.model.AnswerDTO;
import com.trivia.api.model.QuestionDTO;
import com.trivia.api.model.QuestionResultDTO;
import com.trivia.api.service.OpenTriviaResponse;
import com.trivia.api.service.OpenTriviaValue;
import com.trivia.api.service.OpenTriviaWrapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private OpenTriviaClient openTriviaClient;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(openTriviaClient);
        
        // Use lenient() for setup that's not used in all tests
        OpenTriviaResponse mockResponse = new OpenTriviaResponse();
        mockResponse.setResponseCode(0);
        List<OpenTriviaValue> results = new ArrayList<>();
        OpenTriviaValue value = new OpenTriviaValue();
        value.setCorrectAnswer("Correct Answer");
        value.setIncorrectAnswers(List.of("Wrong 1", "Wrong 2", "Wrong 3"));
        results.add(value);
        mockResponse.setResults(results);

        lenient().when(openTriviaClient.fetchQuestions(anyInt())).thenReturn(mockResponse);
    }

    @Test
    void getQuestions_ShouldReturnFormattedQuestions() {
        OpenTriviaWrapperDTO result = questionService.getQuestions(1);

        assertThat(result).isNotNull();
        assertThat(result.getResponse_code()).isZero();
        assertThat(result.getResults()).hasSize(1);
        assertThat(result.getResults().get(0).getAnswers()).hasSize(4);
    }

    @Test
    void getQuestions_ShouldThrowException_WhenAmountIsInvalid() {
        assertThatThrownBy(() -> questionService.getQuestions(0))
            .isInstanceOf(TriviaApiException.class)
            .hasMessage("Amount must be greater than 0");

        assertThatThrownBy(() -> questionService.getQuestions(null))
            .isInstanceOf(TriviaApiException.class)
            .hasMessage("Amount must be greater than 0");
    }

    @Test
    void getQuestions_ShouldThrowException_WhenApiReturnsNull() {
        when(openTriviaClient.fetchQuestions(anyInt())).thenReturn(null);
        
        assertThatThrownBy(() -> questionService.getQuestions(1))
            .isInstanceOf(TriviaApiException.class)
            .hasMessage("Received null response from OpenTriviaDatabase API");
    }

    @Test
    void checkAnswers_ShouldReturnCorrectResults() {
        questionService.getQuestions(1); // Populate internal maps
        List<AnswerDTO> answers = List.of(new AnswerDTO(0, "Correct Answer"));

        Map<Integer, QuestionResultDTO> results = questionService.checkAnswers(answers);

        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        QuestionResultDTO result = results.get(0);
        assertThat(result.getIsCorrect()).isTrue();
        assertThat(result.getCorrectAnswer()).isEqualTo("Correct Answer");
    }

    @Test
    void checkAnswers_ShouldHandleIncorrectAnswers() {
        questionService.getQuestions(1); // Populate internal maps
        List<AnswerDTO> answers = List.of(new AnswerDTO(0, "Wrong Answer"));

        Map<Integer, QuestionResultDTO> results = questionService.checkAnswers(answers);

        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        QuestionResultDTO result = results.get(0);
        assertThat(result.getIsCorrect()).isFalse();
        assertThat(result.getCorrectAnswer()).isEqualTo("Correct Answer");
    }

    @Test
    void checkAnswers_ShouldThrowException_WhenNoQuestionsAvailable() {
        List<AnswerDTO> answers = List.of(new AnswerDTO(0, "Some Answer"));

        assertThatThrownBy(() -> questionService.checkAnswers(answers))
            .isInstanceOf(TriviaApiException.class)
            .hasMessage("No questions available. Please fetch questions first.");
    }

    @Test
    void checkAnswers_ShouldThrowException_WhenInvalidQuestionId() {
        questionService.getQuestions(1); // Populate internal maps
        List<AnswerDTO> answers = List.of(new AnswerDTO(999, "Some Answer"));

        assertThatThrownBy(() -> questionService.checkAnswers(answers))
            .isInstanceOf(TriviaApiException.class)
            .hasMessageContaining("Invalid question ID: 999");
    }

    @Test
    void checkAnswers_ShouldThrowException_WhenNoAnswersProvided() {
        assertThatThrownBy(() -> questionService.checkAnswers(null))
            .isInstanceOf(TriviaApiException.class)
            .hasMessage("No answers provided");

        assertThatThrownBy(() -> questionService.checkAnswers(List.of()))
            .isInstanceOf(TriviaApiException.class)
            .hasMessage("No answers provided");
    }
}
