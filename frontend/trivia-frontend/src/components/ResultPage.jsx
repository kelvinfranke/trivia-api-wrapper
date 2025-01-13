import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Results from './Results.jsx'

function ResultPage({ answers }) {
  const [results, setResults] = useState(null);

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
        const data = await response.json();
        setResults(data);
      } catch (error) {
        console.error('Error submitting answers:', error);
      }
    };

    sendAnswers();
    
  }, [answers]);
  
  if (!results) {
    return <div>Loading results</div>;
  }

  return (
    <div>
      <p>Finished!</p>
      <Results results={results} />
    </div>
  );
}

ResultPage.propTypes = {
  answers: PropTypes.object.isRequired
};

export default ResultPage;