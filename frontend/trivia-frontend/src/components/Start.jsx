import { useState } from 'react';
import PropTypes from 'prop-types';

function Start({ setQuestions, setScreen }) {
  const [numberOfQuestions, setNumberOfQuestions] = useState(0);
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  function decodeHtml(html) {
    var txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
  }

  function handleInputChange(event) {
    setError('');
    const value = parseInt(event.target.value);
    if (value < 0) {
      setError('Number of questions cannot be negative');
    }
    setNumberOfQuestions(value);
  }

  async function handleStart() {
    setError('');
    if (numberOfQuestions <= 0) {
      setError('Please enter a valid number of questions');
      return;
    }

    setIsLoading(true);
    try {
      const response = await fetch(
        `http://localhost:8080/questions?amount=${numberOfQuestions}`
      );
      if (!response.ok) {
        throw new Error(`HTTP error withs status: ${response.status}`);
      }
      const data = await response.json();
      const decodedData = {
        ...data,
        results: data.results.map((item) => ({
          ...item,
          question: decodeHtml(item.question),
          answers: item.answers.map((answer) => decodeHtml(answer)),
        })),
      };
      setQuestions(decodedData);
      setScreen("questions");
    } catch (err) {
      console.error(err);
      setError('Failed to fetch questions. Please try again.');
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className="start-section">
      <label className="start-label">
        <h3>How many questions?</h3>
        <input
          className="start-input"
          value={numberOfQuestions}
          onChange={handleInputChange}
          type="number"
          min="1"
          disabled={isLoading}
        />
      </label>
      {error && <p className="error-message" style={{ color: 'red' }}>{error}</p>}
      <button onClick={handleStart} disabled={isLoading || numberOfQuestions <= 0 || !numberOfQuestions}>
        {isLoading ? 'Loading...' : 'Start'}
      </button>
    </div>
  );
}

Start.propTypes = {
  setQuestions: PropTypes.func.isRequired,
  setScreen: PropTypes.func.isRequired
};

export default Start;
