function onSubmitButtonClick()
{
    let usernameElement = document.getElementById("username");
    if (usernameElement.value.length > 0)
    {
        let username = usernameElement.value;
        localStorage.setItem("username", username);
        
        window.location = "../2gameScreen/2_gamePage.html";
    }
    else
    {
        let errorElement = document.getElementById("errorMessage");
        errorElement.innerHTML = "Invalid input! <br> Please enter a username!"
    }
}

function onLoad()
{
    let usernameElement = document.getElementById("username");
    if (usernameElement.value.length > 0)
    {
        let username = localStorage.getItem("username");
        usernameElement.value = username;
    }
}