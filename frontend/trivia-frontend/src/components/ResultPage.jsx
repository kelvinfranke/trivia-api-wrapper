import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Results from './Results.jsx'

function ResultPage({ answers, setScreen }) {
  const [results, setResults] = useState(null);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const arrayFormat = Object.entries(answers).map(([id, answer]) => ({
      questionId: parseInt(id, 10),
      answer: answer
    }));

    const sendAnswers = async () => {
      try {
        const response = await fetch('http://localhost:8080/checkanswers', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(arrayFormat)
        });
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        if (!data || typeof data !== 'object' || data.length === 0) {
          throw new Error('Invalid response format');
        }
        
        setResults(data);
      } catch (error) {
        console.error('Error submitting answers:', error);
        setError('Failed to submit answers. Please try again.');
      } finally {
        setIsLoading(false);
      }
    };

    sendAnswers();
  }, [answers]);
  
  if (error) {
    return (
      <div className="error-container">
        <p className="error-message" style={{ color: 'red' }}>{error}</p>
        <button onClick={() => setScreen('start')}>Back to Start</button>
      </div>
    );
  }

  if (isLoading) {
    return <div className="loading">Calculating your results...</div>;
  }

  return (
    <div className="results-container">
      <Results results={results} />
      <button onClick={() => setScreen('start')} className="restart-button">
        Try Again
      </button>
    </div>
  );
}

ResultPage.propTypes = {
  answers: PropTypes.object.isRequired,
  setScreen: PropTypes.func.isRequired
};

export default ResultPage;