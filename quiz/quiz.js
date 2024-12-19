let currentQuestionIndex = 0;
let score = 0;
let questions = [];

async function loadQuestions(category) {
  fetch("questions.json")
    .then(response => response.json())
    .then(data => {
      const categoryQuestions = data.find(item => item.category === category);
      if (categoryQuestions) {
        questions = shuffleArray(categoryQuestions.questions).slice(0, 10); 
        startQuiz();
      } else {
        console.error("Category not found.");
      }
    })
    .catch(error => console.error("Error loading questions:", error));
}


function shuffleArray(array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
}

function startQuiz() {
  document.getElementById("quiz-container").innerHTML = `
    <div id="question"></div>
    <div id="choices"></div>
    <div id="feedback"></div>
    <button id="next-btn" onclick="nextQuestion()" style="display:none;">Next Question</button>
  `;
  showQuestion();
}

function showQuestion() {
  const questionContainer = document.getElementById("question");
  const choicesContainer = document.getElementById("choices");
  const feedbackContainer = document.getElementById("feedback");

  if (currentQuestionIndex < questions.length) {
    const currentQuestion = questions[currentQuestionIndex];
    questionContainer.textContent = `${currentQuestionIndex + 1}. ${currentQuestion.question}`;
    choicesContainer.innerHTML = "";
    feedbackContainer.innerHTML = ""; 
    document.getElementById("next-btn").style.display = "none"; 

    currentQuestion.options.forEach(choice => {
      const button = document.createElement("button");
      button.textContent = choice;
      button.onclick = () => checkAnswer(choice, button);
      choicesContainer.appendChild(button);
    });
  } else {
    showResults();
  }
}

function checkAnswer(selectedChoice, selectedButton) {
  const currentQuestion = questions[currentQuestionIndex];
  const feedbackContainer = document.getElementById("feedback");

  if (selectedChoice === currentQuestion.answer) {
    score++;
    feedbackContainer.textContent = `Correct! The right answer is: ${currentQuestion.answer}`;
  } else {
    feedbackContainer.textContent = `Incorrect. The right answer is: ${currentQuestion.answer}`;
  }

 
  const buttons = document.querySelectorAll("#choices button");
  buttons.forEach(button => button.disabled = true);

  document.getElementById("next-btn").style.display = "inline";
}

function nextQuestion() {
  currentQuestionIndex++;
  showQuestion();
}

function showResults() {
  const quizContainer = document.getElementById("quiz-container");
  quizContainer.innerHTML = `
    <h2>Quiz Completed!</h2>
    <p>Your score is ${score} out of ${questions.length}.</p>
    <button onclick="restartQuiz()">Restart Quiz</button>
    <button onclick="selectQuiz()">Select Another Quiz</button>
  `;
}

function restartQuiz() {
  currentQuestionIndex = 0;
  score = 0;
  startQuiz();
}

function selectQuiz() {
  window.location.href = "quizzes.html"; 
}

