let currentQuestionIndex = 0;
let score = 0;
let questions = [];

function loadQuestions(category) {
  fetch("questions.json")
    .then(response => response.json())
    .then(data => {
      questions = data.find(q => q.category === category)?.questions || [];
      if (questions.length > 0) {
        questions = shuffleArray(questions);
        startQuiz();
      } else {
        document.getElementById("quiz-container").innerHTML = "<p>Асуулт олдсонгүй!</p>";
      }
    })
    .catch(error => console.error("Error loading questions:", error));
}

function shuffleArray(array) {
  return array.sort(() => Math.random() - 0.5);
}

function startQuiz() {
  currentQuestionIndex = 0;
  score = 0;
  document.getElementById("quiz-container").innerHTML = `
    <div id="question"></div>
    <div id="choices"></div>
    <div id="feedback"></div>
    <button id="next-btn" onclick="nextQuestion()" style="display:none;">Дараах</button>
  `;
  showQuestion();
}

function showQuestion() {
  const questionContainer = document.getElementById("question");
  const choicesContainer = document.getElementById("choices");
  const feedbackContainer = document.getElementById("feedback");

  if (currentQuestionIndex < questions.length) {
    const currentQuestion = questions[currentQuestionIndex];
    questionContainer.innerHTML = `<h2>${currentQuestionIndex + 1}. ${currentQuestion.question}</h2>`;

    if (currentQuestion.image) {
      questionContainer.innerHTML += `<img src="${currentQuestion.image}" alt="Зураг" style="width:200px;">`;
    }

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
    feedbackContainer.innerHTML = `<p style="color:green;">Зөв! ✅</p>`;
  } else {
    feedbackContainer.innerHTML = `<p style="color:red;">Буруу ❌. Зөв хариулт: ${currentQuestion.answer}</p>`;
  }

  document.querySelectorAll("#choices button").forEach(button => button.disabled = true);
  document.getElementById("next-btn").style.display = "inline";
}

function nextQuestion() {
  currentQuestionIndex++;
  showQuestion();
}

function showResults() {
  const quizContainer = document.getElementById("quiz-container");
  quizContainer.innerHTML = `
    <h2>Шалгалт дууслаа!</h2>
    <p>Таны оноо: <strong>${score} / ${questions.length}</strong></p>
    <button onclick="restartQuiz()">Дахин эхлүүлэх</button>
    <button onclick="selectQuiz()">Бусад тест сонгох</button>
  `;
}

function restartQuiz() {
  startQuiz();
}

function selectQuiz() {
  window.location.href = "quizzes.html";
}
