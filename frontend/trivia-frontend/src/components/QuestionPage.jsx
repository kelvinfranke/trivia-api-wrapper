import { useState } from 'react';
import PropTypes from 'prop-types';

function QuestionPage({ questions, setAnswers, setScreen }) {
  const [currentQuestionIdx, setCurrentQuestionIdx] = useState(0);
  const [localAnswers, setLocalAnswers] = useState({});
  const [selectedAnswerIdx, setSelectedAnswerIdx] = useState(null);
  const [error, setError] = useState('');

  if (!questions || !questions.results || !Array.isArray(questions.results) || questions.results.length === 0) {
    return (
      <div className="error-container">
        <p>Error: No questions available</p>
        <button onClick={() => setScreen('start')}>Back to Start</button>
      </div>
    );
  }

  const currentQuestion = questions.results[currentQuestionIdx];
  if (!currentQuestion || !currentQuestion.answers) {
    return (
      <div className="error-container">
        <p>Error: Question data is not correct. Did something go wrong with fetching?</p>
        <button onClick={() => setScreen('start')}>Back to Start</button>
      </div>
    );
  }

  const handleAnswerClick = (questionId, answer, answerIdx) => {
    setLocalAnswers(prev => ({
      ...prev,
      [questionId]: answer,
    }));
    setSelectedAnswerIdx(answerIdx);
  };

  function goToPreviousQuestion() {
    if (currentQuestionIdx > 0) {
      setSelectedAnswerIdx(-1);
      setCurrentQuestionIdx(currentQuestionIdx - 1);
    }
  }

  function goToNextQuestion() {
    if (currentQuestionIdx < questions.results.length - 1) {
      setSelectedAnswerIdx(-1);
      setCurrentQuestionIdx(currentQuestionIdx + 1);
    }
  }

  const handleFinish = () => {
    setError('');
    const totalQuestions = questions.results.length;
    const answeredQuestions = Object.keys(localAnswers).length;

    if (answeredQuestions < totalQuestions) {
      setError(`Please answer all questions before finishing. ${answeredQuestions}/${totalQuestions} answered.`);
      return;
    }
    setAnswers(localAnswers);
    setScreen('finish');
  };

  return (
    <div className="question-section">
      <div className="progress-indicator">
        Question {currentQuestionIdx + 1} of {questions.results.length}
      </div>
      <h3 className="question-text">{currentQuestion.question}</h3>
      <ul className="answers">
        {currentQuestion.answers.map((answer, i) => (
          <li
            onClick={() => handleAnswerClick(currentQuestionIdx, answer, i)}
            className={selectedAnswerIdx === i ? "answer selected-answer" : "answer"}
            key={i}
          >{answer}</li>
        ))}
      </ul>
      {error && <p className="error-message" style={{ color: 'red' }}>{error}</p>}
      <div className="buttons">
        <button className="btn" onClick={goToPreviousQuestion} disabled={currentQuestionIdx === 0}>Previous</button>
        <button className="btn" onClick={goToNextQuestion} hidden={currentQuestionIdx === questions.results.length - 1}>Next</button>
        <button className="btn" onClick={handleFinish} hidden={currentQuestionIdx < questions.results.length - 1}>Finish</button>
      </div>
    </div>
  )
}

QuestionPage.propTypes = {
  questions: PropTypes.object.isRequired,
  setAnswers: PropTypes.func.isRequired,
  setScreen: PropTypes.func.isRequired
};

export default QuestionPage;