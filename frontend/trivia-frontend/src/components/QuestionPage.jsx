import { useState } from 'react';
import PropTypes from 'prop-types';

function QuestionPage({ questions, setAnswers, setScreen }) {
  const [currentQuestionIdx, setCurrentQuestionIdx] = useState(0);
  const [localAnswers, setLocalAnswers] = useState({});
  const [selectedAnswerIdx, setSelectedAnswerIdx] = useState(null)

  if (!questions || !questions.results) {
    return <div>Loading</div>;
  }

  const currentQuestion = questions.results[currentQuestionIdx];

  function decodeHtml(html) {
    var txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
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
    const totalQuestions = questions.results.length;
    const answeredQuestions = Object.keys(localAnswers).length;

    if (answeredQuestions < totalQuestions) {
      alert('Please answer all questions before finishing.');
      return;
    }
    setAnswers(localAnswers);
    setScreen('finish');
  };

  return (
    <div className="question-section">
      {/* <h3 className="question-text">{decodeHtml(currentQuestion.question)}</h3> */}
      <h3 className="question-text">{currentQuestion.question}</h3>
      <ul className="answers">
        {currentQuestion.answers.map((answer, i) => (
          <li
            onClick={() => handleAnswerClick(currentQuestionIdx, answer, i)}
            className={selectedAnswerIdx === i ? "answer selected-answer" : "answer"}
            key={i}
          >{answer}</li>
          // >{decodeHtml(answer)}</li>
        ))}
      </ul>
      <div className="buttons">
        <button className="btn" onClick={goToPreviousQuestion} disabled={currentQuestionIdx == 0}>Previous</button>
        <button className="btn" onClick={goToNextQuestion} hidden={currentQuestionIdx == questions.results.length - 1}>Next</button>
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