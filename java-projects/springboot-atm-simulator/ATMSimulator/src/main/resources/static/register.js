function registerClick() {
  let num1 = $("#cardNr").val();
  let num2 = $("#pass").val();

  const cardRegex = /^\d{10}$/;
  const pinRegex = /^\d{4}$/;

  if (!cardRegex.test(num1)) {
    $("#loginMessage").text("Card Nr must be exactly 10 digits!").show();
    return;
  }

  if (!pinRegex.test(num2)) {
    $("#loginMessage").text("PIN must be exactly 4 digits!").show();
    return;
  }

  const payload = {
    cardNumber: num1,
    pin: num2
  };

  console.log("Register payload:", payload);

  $.ajax({
    type: "POST",
    url: "/api/atm_register",
    contentType: "application/json",
    dataType: "json",
    data: JSON.stringify(payload),
    success: function (response) {

      if (response.status === "success") {
        $("#loginMessage")
          .text("Registered successfully! You can log in now.")
          .show();
      }
      else if (response.status === "exists") {
        $("#loginMessage").text("User already exists!").show();
      }
      else {
        $("#loginMessage").text("Registration failed!").show();
      }

    },
    error: function (xhr) {
      console.error("Register error:", xhr.responseText);
      $("#loginMessage").text("Server error during registration.").show();
    }
  });
}