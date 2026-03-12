let time = 0;
let timerShowed = false;
let timerClicked = false;

function startTimer(difficultySpeed)
{
    let timer = setInterval(() => 
    {
        time = Number(localStorage.getItem("timer"));
        if (time == 1)
        { 
            clearInterval(timer);
            localStorage.setItem("timer", 0);
        }
        else if (time <= 20 && !timerShowed)
            showBonusTimeButton(difficultySpeed)
     time--;

     if (!timerClicked)
        document.getElementById("timer").textContent = "Timer: " + time + "s";

     localStorage.setItem("timer", time);
    }, 1000);
}

function showBonusTimeButton(difficultySpeed)
{
    if (timerClicked)
        return;

    let chance = Math.floor(Math.random() * 6);
    if (chance == 0)
    {
        timerShowed = true;
        let timerButton = document.getElementById("timerButton");
        timerButton.style.opacity = 1; timerButton.disabled = false;

        spawnNodeWithinBorders(timerButton, 80);

        setTimeout(() =>
        {
            timerButton.style.opacity = 0; timerButton.disabled = true;
            timerShowed = false;
        }, difficultySpeed);
    }
}

function onTimerClick()
{
    timerClicked = true;

    time = Number(localStorage.getItem("timer"));
    time += 5;
    localStorage.setItem("timer", time);

    let timer = document.getElementById("timer");
    timer.style.color = "rgb(238, 255, 0)";
    timer.style.fontSize = "4.5vh";
    timer.style.paddingTop = "1px";
    timer.textContent = "Time: " + time + "s +5s!"

    let timerButton = document.getElementById("timerButton");
    timerButton.style.opacity = 0; timerButton.disabled = true;

    setTimeout(() =>
    {
        timer.style.color = "rgb(202, 238, 255)";
        timer.style.fontSize = "4vh";
        timer.style.paddingTop = "1vh";
        timer.textContent = "Time: " + time + "s";
        timerClicked = false;
    }, 800);
}