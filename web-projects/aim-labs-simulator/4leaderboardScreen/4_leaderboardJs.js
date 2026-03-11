function onLeaderboardLoad()
{
    let username = localStorage.getItem("username");
    let finalScore = localStorage.getItem("points");
    let chosenDifficulty = localStorage.getItem("difficulty");

    let table = document.getElementById("leaderBoardTable");
    let Table = $(table);

    let row = $("<tr>");

    let usernameCell = $("<td>").addClass("leaderBoardUsername-design").text(username);
    let scoreCell = $("<td>").addClass("leaderBoardPoints-design").text(finalScore);
    let difficultyCell = $("<td>").addClass("leaderBoardDifficulty-design").text(chosenDifficulty);

    row.append(usernameCell);
    row.append(scoreCell);
    row.append(difficultyCell);

    Table.append(row);

    closeLeaderBoardBorder(table);
}

function closeLeaderBoardBorder(table) 
{
    let $table = $(table);

    let $row = $("<tr>");

    for (let i = 0; i < 3; i++) 
    {
        $row.append($("<td>").css("border-top", "5px solid rgb(180, 50, 33)"));
    }

    $table.append($row);
}



function onBackClick()
{
    window.location = "../3gameEndScreen/3_gameEndPage.html";
}