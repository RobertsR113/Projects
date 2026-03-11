$(document).ready(function () {
    loadATMBalance();
});

function loadATMBalance() {
    $.ajax({
        type: "GET",
        url: "/api/balance",
        dataType: "json",
        success: function (response) {
            $("#atmBalance").text(response.balance);
        },
        error: function () {
            $("#atmBalance").text("Error");
        }
    });
}

function refillATM() {

    const payload = {
        "500": parseInt($("#fivehundred").val()) || 0,
        "100": parseInt($("#hundred").val()) || 0,
        "50": parseInt($("#fifty").val()) || 0,
        "20": parseInt($("#twenty").val()) || 0,
        "10": parseInt($("#ten").val()) || 0,
        "5": parseInt($("#five").val()) || 0
    };

    console.log("Refill payload:", payload);

    $.ajax({
        type: "POST",
        url: "/api/refill",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function () {
            $("#example").css("color", "green").text("ATM refilled successfully!");
            loadATMBalance();
        },
        error: function () {
            $("#example").css("color", "red").text("Refill failed!");
        }
    });
}