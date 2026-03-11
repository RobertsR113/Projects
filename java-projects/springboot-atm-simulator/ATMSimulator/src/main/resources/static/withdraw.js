function formatBanknotes(banknotes) {
  return Object.entries(banknotes)
    .map(([denomination, count]) => `${count} * ${denomination}€`)
    .join("<br>");
}

$(document).ready(function () {
  const urlParams = new URLSearchParams(window.location.search);
  const cardNumber = urlParams.get("cardNumber");

  if (!cardNumber) {
    console.error("Card number is missing from URL!");
    $("#cardNumber").text("Error: Card not found");
    return;
  }

  $("#cardNumber").text(cardNumber);
  fetchBalance(cardNumber);

  // Show withdrawal result after reload
  const withdrawnAmount = sessionStorage.getItem("withdrawnAmount");
  const banknotes = sessionStorage.getItem("banknotes");

  if (withdrawnAmount && banknotes) {
    $("#withdrawResult").html(
      `Withdrawn: ${withdrawnAmount} €<br>Banknotes:<br>${formatBanknotes(
        JSON.parse(banknotes)
      )}`
    );

    sessionStorage.removeItem("withdrawnAmount");
    sessionStorage.removeItem("banknotes");
  }

  $("#withdrawButtonMin").click(() => handleWithdrawal("min"));
  $("#withdrawButtonMax").click(() => handleWithdrawal("max"));
});

function fetchBalance(cardNumber) {
  $.get("/api/getBalance", { cardNumber: cardNumber })
    .done(function (data) {
      if (data.balance !== undefined) {
        $("#balance").text(data.balance);
      } else {
        $("#balance").text("Error loading balance");
      }
    })
    .fail(function () {
      $("#balance").text("Error loading balance");
    });
}

function handleWithdrawal(type) {
  const cardNumber = $("#cardNumber").text();
  const amount = $("#withdrawAmount").val();

  if (!amount || isNaN(amount) || amount <= 0) {
    $("#withdrawResult").html("Please enter a valid amount.");
    return;
  }

  $.ajax({
    url: "/api/withdraw",
    method: "POST",
    data: JSON.stringify({
      amount: amount,
      cardNumber: cardNumber,
      type: type,
    }),
    contentType: "application/json",
    success: function (response) {
      if (response.status === "success") {
        sessionStorage.setItem("withdrawnAmount", amount);
        sessionStorage.setItem(
          "banknotes",
          JSON.stringify(response.banknotes)
        );
        location.reload();
      } else {
        $("#withdrawResult").html(
          response.message || "Error during withdrawal."
        );
      }
    },
    error: function () {
      $("#withdrawResult").html("An error occurred. Please try again.");
    },
  });
}