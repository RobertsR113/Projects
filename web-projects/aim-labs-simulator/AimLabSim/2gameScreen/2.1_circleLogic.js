let circleInterval = null;
let animationSpeed = 0;
let Points = 0;

function startCircleInterval(difficultySpeed)
{
    animationSpeed = difficultySpeed;
    circleInterval = setInterval(() => 
    {
        clearInterval(clickSpeedInterval);
        clickSpeed = 0;

        let timer = Number(localStorage.getItem("timer"));
        if (timer <= 0)
        {
            stopCircles();
            return;
        }
        showCircle();
    }, (animationSpeed-100));
}

function showCircle()
{
    let circleButton = document.getElementById("circleButton");

    circleButton.style.opacity = 0; 
    circleButton.disabled = true;

    circleButton.classList.remove("circle-animation");
    void circleButton.offsetWidth;
    circleButton.classList.add("circle-animation");
    circleButton.style.animationDuration = animationSpeed + "ms";

    circleButton.style.backgroundColor = getRandomColor();

    spawnNodeWithinBorders(circleButton, 100);

    circleButton.style.opacity = 1; 
    circleButton.disabled = false;
    startOnClickCounting();
}

function stopCircles()
{
    let circleButton = document.getElementById("circleButton");

    clearInterval(circleInterval);
    clearInterval(clickSpeedInterval);
    circleInterval = null;
    clickSpeedInterval = null;
    clickSpeed = 0;

    circleButton.classList.remove("circle-animation");
    circleButton.style.opacity = 0;
    circleButton.disabled = true;
    endGame(Points);
}





let clickSpeed = 0;
let clickSpeedInterval = null;
function onCircleClick()
{
    let timer = Number(localStorage.getItem("timer"));
    if (timer <= 0)
        return;

    circleShowed = false;
    givePoints(clickSpeed);

    clickSpeed = 0;
    clearInterval(clickSpeedInterval);

    clearInterval(circleInterval);
    startCircleInterval(animationSpeed);
    showCircle();
}

function startOnClickCounting()
{
    clickSpeedInterval = setInterval(() => 
    { 
        clickSpeed += 50; 
    }, 50);
}

function givePoints(clickSpeed)
{

    if (clickSpeed <= animationSpeed /3)
    {
        circleText(10);
        Points += 10;
    }
    else if (clickSpeed <= animationSpeed / 2)
    {
        circleText(5);
        Points += 5;
    }
    else
    {
        circleText(1);
        Points += 1;
    }

    let points = Number(localStorage.getItem("points"));
    points = Points;
    localStorage.setItem("points", points);
    
    document.getElementById("points").textContent = "Points: " + points;
}

function circleText(Points)
{
    let circleButton = document.getElementById("circleButton");
    let circleText = document.getElementById("circleText");
    
    circleText.style.left = circleButton.style.left;
    circleText.style.top  = circleButton.style.top;

    circleText.style.color = getRandomColor();
    circleText.textContent = "+" + Points;
    circleText.style.opacity = 1;

    setTimeout(() => 
    {
        circleText.style.opacity = 0;
    }, 600);
}