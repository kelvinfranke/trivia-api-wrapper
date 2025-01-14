# Trivia Application

A full-stack trivia application built with Spring Boot backend and React frontend. The application fetches trivia questions from the Open Trivia Database, presents them to users, and checks their answers.

## Project Structure

```
api/
├── src/                 # Spring Boot backend
│   ├── main/java        # Backend source code
│   └── test/java        # Backend tests
└── frontend/            # React frontend
    └── trivia-frontend/ # Frontend source code
```

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- npm or yarn

## Running the Backend

1. Navigate to the root directory (`api/`):
```bash
cd api
```

2. Run the Spring Boot application:
```bash
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`

## Running the Frontend

1. Navigate to the frontend directory:
```bash
cd frontend/trivia-frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:5173`

## Using the Application

1. Open `http://localhost:5173` in your browser
2. Enter the number of questions you want to answer
3. Answer each question by clicking on your chosen answer
4. Navigate through questions using Previous/Next buttons
5. Submit your answers to see your results

## API Endpoints

- `GET /questions?amount={number}` - Get specified number of trivia questions
- `POST /checkanswers` - Submit answers for checking

## Technologies Used

- Backend:
  - Spring Boot
  - Java 17
  - JUnit for testing
  - RestTemplate for API calls

- Frontend:
  - React
  - Vite
  - PropTypes for type checking
