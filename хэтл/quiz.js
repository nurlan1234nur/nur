const htmlQuestions = [
    { question: "What does HTML stand for?", answer: "HyperText Markup Language", options: ["HyperText Markup Language", "Home Tool Markup Language", "Hyperlink Text Management"] },
    { question: "Which tag is used to define a paragraph?", answer: "<p>", options: ["<p>", "<h1>", "<div>", "<span>"] },
    { question: "Which attribute is used to specify an image source?", answer: "src", options: ["src", "alt", "href", "link"] }
];

const cssQuestions = [
    { question: "What does CSS stand for?", answer: "Cascading Style Sheets", options: ["Cascading Style Sheets", "Creative Style Sheets", "Colorful Style Sheets"] },
    { question: "Which property changes the text color?", answer: "color", options: ["font-color", "color", "text-color", "background-color"] }
];

const jsQuestions = [
    { question: "Which symbol is used for comments in JavaScript?", answer: "//", options: ["//", "/* */", "<!-- -->", "#"] },
    { question: "How do you declare a JavaScript function?", answer: "function myFunction()", options: ["function myFunction()", "func myFunction()", "function:myFunction()", "myFunction()"] }
];

function startQuiz() {
    let questions;
    if (quizType === "html") questions = htmlQuestions;
    else if (quizType === "css") questions = cssQuestions;
    else if (quizType === "js") questions = jsQuestions;

    const container = document.getElementById('quiz-container');
    container.innerHTML = '';

    questions.forEach((q, index) => {
        const questionDiv = document.createElement('div');
        questionDiv.innerHTML = `<p>${index + 1}. ${q.question}</p>`;
        q.options.forEach(opt => {
            questionDiv.innerHTML += `<label><input type="radio" name="q${index}" value="${opt}"> ${opt}</label><br>`;
        });
        container.appendChild(questionDiv);
    });

    container.innerHTML += `<button onclick="submitQuiz(${questions.length})">Submit</button>`;
}

function submitQuiz(totalQuestions) {
    let score = 0;
    const answers = document.querySelectorAll('input[type="radio"]:checked');
    answers.forEach((a, i) => {
        if (a.value === htmlQuestions[i].answer) score++;
    });
    alert(`Your score: ${score}/${totalQuestions}`);
}
