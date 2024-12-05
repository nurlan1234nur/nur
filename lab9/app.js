const words = ["javascript","nurlan", "hangman", "programming", "developer","aysu","itu","labaro"];
const word = words[Math.floor(Math.random() * words.length)];
let inWord = Array(word.length).fill("_");
let livecount = 0;

const maxLives = 6; // Амь
const wordDisplay = document.getElementById("word-display");
const keyboard = document.getElementById("keyboard");
const message = document.getElementById("message");
const canvas = document.getElementById("hangman-canvas");
const ctx = canvas.getContext("2d");
const livesDisplay = document.getElementById("lives"); // Амийн тоог харуулах

function updateWordDisplay() {
    wordDisplay.textContent = inWord.join(" ");
}

// Update lives display
function updateLivesDisplay() {
    livesDisplay.textContent = `Lives: ${maxLives - livecount}`;
}

// Create keyboard buttons
function createKeyboard() {
    for (let i = 65; i <= 90; i++) {    
        const button = document.createElement("button");
        button.classList.add("button");
        button.textContent = String.fromCharCode(i);
        button.onclick = function() { handleGuess(button.textContent.toLowerCase());
};        keyboard.appendChild(button);
    }
}

function handleGuess(letter) {
    let correct = false;

    for (let i = 0; i < word.length; i++) {
        if (word[i] === letter) {
            inWord[i] = letter;
            correct = true;
        }
    }

    const buttons = document.querySelectorAll(".button");  
    buttons.forEach((button) => {
        if (button.textContent.toLowerCase() === letter) {
            if (correct) {
                button.classList.add("correct");
            } else {
                button.classList.add("uncorrect");
            }
            disableLetter(button);  
        }
    });

    if (!correct) {
        livecount++;
        updateLivesDisplay();
        drawHangman(livecount);
        if (livecount === maxLives) {
            message.textContent = `Game Over! The word was: ${word}`;
            disableButtons();
        }
    } else if (!inWord.includes("_")) {
        message.textContent = "Congratulations! You won!";
        disableButtons();
    }

    updateWordDisplay();
}

function disableLetter(button) {
    button.disabled = true;  
}

function disableButtons() {
    const buttons = document.querySelectorAll(".button");
    buttons.forEach((button) => button.disabled = true);  
}


function disableLetter(letter) {
    const buttons = document.querySelectorAll(".button");
    buttons.forEach((button) => {
        if (button.textContent.toLowerCase() === letter) {
            button.disabled = true;
        }   
    });
}

function disableButtons() {
    const buttons = document.querySelectorAll(".button");
    buttons.forEach((button) => (button.disabled = true));
}

function drawHangman(step) {
    ctx.lineWidth = 2;
    ctx.strokeStyle = "#333";

    switch (step) {
        case 1: 
            ctx.beginPath();
            ctx.arc(150, 70, 20, 0, Math.PI * 2, true);
            ctx.stroke();
            break;
        case 2: 
            ctx.beginPath();
            ctx.moveTo(150, 90);
            ctx.lineTo(150, 160);
            ctx.stroke();
            break;
        case 3: 
            ctx.beginPath();
            ctx.moveTo(150, 100);
            ctx.lineTo(120, 130);
            ctx.stroke();
            break;
        case 4: 
            ctx.beginPath();
            ctx.moveTo(150, 100);
            ctx.lineTo(180, 130);
            ctx.stroke();
            break;
        case 5: 
            ctx.beginPath();
            ctx.moveTo(150, 160);
            ctx.lineTo(120, 200);
            ctx.stroke();
            break;
        case 6: 
            ctx.beginPath();
            ctx.moveTo(150, 160);
            ctx.lineTo(180, 200);
            ctx.moveTo(140, 60); 
            ctx.lineTo(160, 80);
            ctx.moveTo(160, 60); 
            ctx.lineTo(140, 80);
            ctx.stroke();
            break;
    }
}

function drawInitialHangman() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.moveTo(10, 240);
    ctx.lineTo(190, 240);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(50, 240);
    ctx.lineTo(50, 20);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(50, 20);
    ctx.lineTo(150, 20);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(150, 20);
    ctx.lineTo(150, 50);
    ctx.stroke();
}

updateWordDisplay();
updateLivesDisplay();
createKeyboard();
drawInitialHangman();
