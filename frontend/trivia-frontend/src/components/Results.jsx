import PropTypes from 'prop-types';

function Results({ results }) {
    const keys = Object.keys(results);
  
    const totalCorrect = keys.filter((key) => results[key]?.isCorrect).length;
  
    function decodeHtml(html) {
        var txt = document.createElement("textarea");
        txt.innerHTML = html;
        return txt.value;
    }

    return (
      <div>
        <h2>
          You got {totalCorrect} out of {keys.length} correct!
        </h2>
        
        {keys.map((id) => {
          const result = results[id] || {};
          const { question, isCorrect, correctAnswer } = result;
          
          if (!question || !correctAnswer) {
            return (
              <div className="result-item error" key={id}>
                <p className="error-message" style={{ color: 'red' }}>
                  Error: Missing data for this question
                </p>
              </div>
            );
          }

          return (
            <div className="result-item" key={id}>
              <h3>{decodeHtml(question)}</h3>
              <p>
                {isCorrect ? "✅ Correct" : "❌ Incorrect"}
              </p>
              <p>
                Correct Answer: <strong>{decodeHtml(correctAnswer)}</strong>
              </p>
              <hr/>
            </div>
          );
        })}
      </div>
    );
}

Results.propTypes = {
    results: PropTypes.object.isRequired
};
  
export default Results;