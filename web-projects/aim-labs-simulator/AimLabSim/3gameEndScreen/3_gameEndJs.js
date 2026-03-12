function showFinalScore()
{
    let finalScore = localStorage.getItem("points");
    let username = localStorage.getItem("username");
    
    document.getElementById("finalScore").textContent = "Final Score: " + finalScore;
    document.getElementById("username").textContent = username; 
}

function onPlayAgainClick()
{
    window.location = "../2gameScreen/2_gamePage.html";
}

function onLeaderboardClick()
{
    window.location = "../4leaderboardScreen/4_leaderboardPage.html";
}