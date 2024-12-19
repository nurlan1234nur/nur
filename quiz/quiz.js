let currentQuestionIndex = 0;
let score = 0;
let questions = [];

// JSON файлыг уншиж асуултуудыг ачаалах функц
function loadQuestions(category) {
  fetch("questions.json")
    .then(response => response.json())
    .then(data => {
      const categoryQuestions = data.find(item => item.category === category);
      if (categoryQuestions) {
        questions = shuffleArray(categoryQuestions.questions).slice(0, 10); // Санамсаргүй 10 асуулт авах
        startQuiz();
      } else {
        console.error("Category not found.");
      }
    })
    .catch(error => console.error("Error loading questions:", error));
}

// Асуултуудыг санамсаргүй дараалалд оруулах функц
function shuffleArray(array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
}

// Quiz эхлүүлэх функц
function startQuiz() {
  document.getElementById("quiz-container").innerHTML = `
    <div id="question"></div>
    <div id="choices"></div>
    <div id="feedback"></div>
    <button id="next-btn" onclick="nextQuestion()" style="display:none;">Next Question</button>
  `;
  showQuestion();
}

// Асуултыг харуулах функц
function showQuestion() {
  const questionContainer = document.getElementById("question");
  const choicesContainer = document.getElementById("choices");
  const feedbackContainer = document.getElementById("feedback");

  if (currentQuestionIndex < questions.length) {
    const currentQuestion = questions[currentQuestionIndex];
    questionContainer.textContent = `${currentQuestionIndex + 1}. ${currentQuestion.question}`;
    
    choicesContainer.innerHTML = "";
    feedbackContainer.innerHTML = ""; // Clear feedback
    document.getElementById("next-btn").style.display = "none"; // Hide Next button

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

// Хариултыг шалгах функц
function checkAnswer(selectedChoice, selectedButton) {
  const currentQuestion = questions[currentQuestionIndex];
  const feedbackContainer = document.getElementById("feedback");

  // Хариулт зөв эсвэл буруу
  if (selectedChoice === currentQuestion.answer) {
    score++;
    feedbackContainer.textContent = `Correct! The right answer is: ${currentQuestion.answer}`;
  } else {
    feedbackContainer.textContent = `Incorrect. The right answer is: ${currentQuestion.answer}`;
  }

  // Сонголт хийсний дараа бүх товчлууруудыг идэвхгүй болгох
  const buttons = document.querySelectorAll("#choices button");
  buttons.forEach(button => button.disabled = true);

  // "Next Question" товчийг харуулах
  document.getElementById("next-btn").style.display = "inline";
}

// Дараагийн асуулт руу шилжих функц
function nextQuestion() {
  currentQuestionIndex++;
  showQuestion();
}

// Үр дүн харуулах функц
function showResults() {
  let photo;
  let a;
const quizContainer = document.getElementById("quiz-container");
if(score>5){
 photo = `https://emojiisland.com/cdn/shop/products/Emoji_Icon_-_Sunglasses_cool_emoji_large.png?v=1571606093`
a = `Yeahh. Score: ${score} / ${questions.length}.`
}else
{
  photo=`https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEj3Gv7npJEIDXjCYebAwIxLdljhNphb5GTgXP5WQm3L7odsa72WgS-VMPpJbI7BDrwGJnD64EFVVQsT8BNUGZ_hn4j_lXGZrc2NYoad911zWuQEkvOTuuoo628GpGJe4t5E_eBnKZ_GEmwF/s1600/this-big-emoji.png`
  a = `Not enough. Score: ${score} / ${questions.length}.`
}
quizContainer.innerHTML = `
   <img src="${photo}" alt="Result Image" style="max-width: 150px; margin: 20px 0;">
  <h2>Quiz complited!</h2>
  <p>${a}</p>
  <button onclick="restartQuiz()">Restart quiz</button>
  <button onclick="selectQuiz()">Select another quiz</button>
`;
}

// Шинээр эхлүүлэх функц
function restartQuiz() {
  currentQuestionIndex = 0;
  score = 0;
  startQuiz();
}

// Quiz сонгох функц (quizzes.html руу шилжих)
function selectQuiz() {
  window.location.href = "quizzes.html"; // quizzes.html руу шилжих
}
