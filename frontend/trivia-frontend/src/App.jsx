import './App.css'
import QuestionPage from './components/QuestionPage.jsx';
import Start from './components/Start.jsx'
import ResultPage from './components/ResultPage.jsx'
import { useState } from 'react';

function App() {
  const [questions, setQuestions] = useState({});
  const [answers, setAnswers] = useState({});
  const [screen, setScreen] = useState("start");

  return (
    <>
      {screen === "start" && (
        <Start
          setQuestions={setQuestions}
          setScreen={setScreen}
        />
      )}

      {screen === "questions" && (
        <QuestionPage
          questions={questions}
          setAnswers={setAnswers}
          setScreen={setScreen}
        />
      )}

      {screen === "finish" && (
        <ResultPage
          answers={answers}
        />
      )}
    </>
  );
}

export default App


// Folder structure
// https://github.com/alan2207/bulletproof-react/blob/master/docs/project-structure.md