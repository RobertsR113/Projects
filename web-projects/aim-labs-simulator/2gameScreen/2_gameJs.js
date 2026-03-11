function onPlayClick() 
{
    var playButton = document.getElementById("play");
    playButton.disabled = true;
    playButton.style.opacity = 0;

    var easyButton = document.getElementById("easy");
    easyButton.style.opacity = 1;  easyButton.disabled = false;
    var hardButton = document.getElementById("hard");
    hardButton.style.opacity = 1; hardButton.disabled = false;
    var insaneButton = document.getElementById("insane");
    insaneButton.style.opacity = 1; insaneButton.disabled = false;
}

function onDifficultyClick(difficulty)
{
    var easyButton = document.getElementById("easy");
    easyButton.style.opacity = 0;  easyButton.disabled = true;
    var hardButton = document.getElementById("hard");
    hardButton.style.opacity = 0; hardButton.disabled = true;
    var insaneButton = document.getElementById("insane");
    insaneButton.style.opacity = 0; insaneButton.disabled = true;

    if (difficulty == 'Easy')
        startGame(2000);
    else if (difficulty == 'Hard')
        startGame(1200);
    else
        startGame(800);

    localStorage.setItem("difficulty", difficulty);
    document.getElementById("chosenDifficulty").textContent = "Difficulty: " + difficulty;
}

function startGame(difficultySpeed)
{
    localStorage.setItem("timer", 30);
    localStorage.setItem("points", 0);
    startCircleInterval(difficultySpeed);
    startTimer(difficultySpeed);
}

function endGame(finalPoints)
{
    document.getElementById("circleButton").remove();
    document.getElementById("timerButton").remove();
    localStorage.setItem("points", finalPoints);

    window.location = "../3gameEndScreen/3_gameEndPage.html";
}

function spawnNodeWithinBorders(nodeIdName, nodeSize)
{
    let border = document.getElementById("border");
    let padding = 30;

    let maxX = border.clientWidth - nodeSize - padding * 2;
    let maxY = border.clientHeight - nodeSize - padding * 2;

    nodeIdName.style.left = padding + Math.random() * maxX + "px";
    nodeIdName.style.top  = padding + Math.random() * maxY + "px";
}

function getRandomColor()
{
    var color1 = 100 + Math.floor(Math.random() * 155);
    var color2 = Math.floor(Math.random() * 255);
    var color3 = Math.floor(Math.random() * 255);

    return "rgb(" + color1 + ", " + color2 + ", " + color3 + ")"
}

function onLoad()
{
    var user = localStorage.getItem("username")
    document.getElementById("username").textContent = user;
}