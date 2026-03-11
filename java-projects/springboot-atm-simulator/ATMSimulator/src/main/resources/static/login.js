let loginAttempts = {};

function loginClick() {
  let num1 = $("#cardNr").val();
  let num2 = $("#pass").val();

  console.log("Card Nr: ", num1);
  console.log("Password: ", num2);

  if (num1 === "admin" && num2 === "admin123") {
    $("#loginMessage").text("Admin login successful!").show();
    window.location.href = "/admin";
    return;
  }

  if (loginAttempts[num1] && loginAttempts[num1] >= 3) {
    $("#loginMessage")
      .text("Card number blocked! Please try again later...!")
      .show();
    return;
  }

  if (!num1) {
    $("#loginMessage").text("Please enter a Card Number!").show();
    return;
  }

  $.ajax({
    type: "GET",
    url: "/api/cards",
    dataType: "json",
    success: function (clients) {
      let cardExists = clients.some((client) => client.cardNumber === num1);

      if (!cardExists) {
        $("#loginMessage")
          .text("Card Number does not exist! Try again.")
          .show();
        return;
      }

      $.ajax({
        type: "POST",
        url: "/api/atm_auth",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({ cardNumber: num1, pin: num2 }),
        success: function (response) {
          if (response.status === "admin") {
            $("#loginMessage").text("Admin login successful!").show();
            window.location.href = "/admin";
          } else if (response.status === "user") {
            $("#loginMessage").text("User login successful!").show();
            window.location.href = "/user?cardNumber=" + num1;
          } else {
            loginAttempts[num1] = (loginAttempts[num1] || 0) + 1;
            $("#loginMessage")
              .text(`Login failed! Try again. (${loginAttempts[num1]})`)
              .show();
          }
        },
        error: function () {
          loginAttempts[num1] = (loginAttempts[num1] || 0) + 1;
          $("#loginMessage")
            .text(`Invalid argument, try again! (${loginAttempts[num1]})`)
            .show();
        },
      });
    },
    error: function () {
      $("#loginMessage")
        .text("Error checking card existence. Try again.")
        .show();
    },
  });
}
