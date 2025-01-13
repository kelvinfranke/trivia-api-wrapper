import PropTypes from 'prop-types';

function Results({ results }) {
    const keys = Object.keys(results);
  
    const totalCorrect = keys.filter((key) => results[key].isCorrect).length;
  
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
          const { question, isCorrect, correctAnswer } = results[id];
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
  