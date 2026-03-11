function loadLogs() {
  $.get("/admin/logs", function (logs) {
    console.log("Logs received:", logs);
    $("#logsList").empty();

    if (logs.length === 0) {
      $("#logsList").append("<li>No logs found.</li>");
    } else {
      logs.forEach((log) => {
        $("#logsList").append("<li>" + log + "</li>");
      });
    }
  }).fail(function () {
    $("#logsList").html("<li>Error loading logs.</li>");
  });
}

$(document).ready(function () {
  loadLogs();
});
