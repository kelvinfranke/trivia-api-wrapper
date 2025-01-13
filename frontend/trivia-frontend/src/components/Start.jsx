import { useState } from 'react';
import PropTypes from 'prop-types';

function Start({ setQuestions, setScreen }) {
  const [numberOfQuestions, setNumberOfQuestions] = useState(0);

  function decodeHtml(html) {
    var txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
  }

  function handleInputChange(event) {
    setNumberOfQuestions(event.target.value);
  }

  async function handleStart() {
    if (numberOfQuestions > 0) {
      try {
        const response = await fetch(
          `http://localhost:8080/questions?amount=${numberOfQuestions}`
        );
        const data = await response.json();
        // const decodedData = data.results.map((item) => {
        //   return {
        //     ...item,
        //     question: decodeHtml(item.question),
        //     answers: item.answers.map((answer) => decodeHtml(answer))
        //   };
        // });
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
      }
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
        />
      </label>
      <button onClick={handleStart}>
        Start
      </button>
    </div>
  );
}

Start.propTypes = {
  setQuestions: PropTypes.func.isRequired,
  setScreen: PropTypes.func.isRequired
};

export default Start;
