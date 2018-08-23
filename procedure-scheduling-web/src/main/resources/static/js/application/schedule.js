var MINUTES_INDEXES = {
    0: 1,
    15: 2,
    30: 3,
    45: 4
};

$(document).ready(function () {

    $("#roomBooking").on("show.bs.modal", function () {

        $.post("/patient/load/available", function (response) {

            console.log(response);
        });
    });
});

function draw(rows) {

    var thead = $(".schedule-table thead");
    thead.html("");
    var headHtml = "";
    headHtml += "<tr>";
    headHtml += "<th>Room Name</th>";
    for (var i = 1; i <= 96; i++) {
        if (i % 4 === 0) {
            var hour = ((i / 4) - 1);
            headHtml += "<th class='table-head-centered' colspan='4'>" + hour + "</th>";
        }
    }
    headHtml += "</tr>";
    thead.html(headHtml);

    var tbody = $(".schedule-table tbody");
    tbody.html("");
    var html = "";

    var positions = [];
    $.each(rows, function (index, element) {

        html += "<tr data-id=" + element.room.id + " class='room-row'>";
        html += "<td class='room-name'>";
        html += element.room.name;
        html += "</td>";
        $.each(element.events, function (index, item) {

            var startTime = new Date(item.plannedStartTime);
            var startIndex = startTime.getHours() * 4 + MINUTES_INDEXES[startTime.getMinutes()];
            var endTime = new Date(item.estimatedEndTime);
            // don't include last element because of offset
            var endIndex = endTime.getHours() * 4 + MINUTES_INDEXES[endTime.getMinutes()] - 1;
            positions.push({
                for: element.room.id,
                item: item,
                fromIdx: startIndex,
                toIdx: endIndex
            });
        });
        for (var i = 1; i <= 96; i++) {
            var position = element.room.id + "_" + i;
            html += "<td class='time-cell' data-position=" + position + "></td>";
        }
        html += "</tr>";
    });
    html += "<tr>";
    for (var j = 1; j <= 97; j++) {
        html += "<td class='dirty-fix-cell'></td>";
    }
    html += "</tr>";

    tbody.html(html);

    var events = [];

    $.each(positions, function (index, element) {

        var id = element.for;
        var event = element.item;
        events[id] = event;
        var eventId = event.id;
        var firstCell = $("td.time-cell[data-position=" + id + "_" + element.fromIdx + "]");
        firstCell.attr("data-event", eventId).attr("colspan", element.toIdx - element.fromIdx + 1).addClass("scheduled-cell");
        for (var i = element.fromIdx + 1; i <= element.toIdx; i++) {
            var position = id + "_" + i;
            $("td.time-cell[data-position=" + position + "]").remove();
        }
        var contentHtml = "<div>";
        contentHtml += "<div class='description-text'>" + event.description + "</div>";
        contentHtml += "<div class='doctor-text'>" + event.doctor.name + "</div>";
        contentHtml += "<div class='patient-text'>" + event.patient.name + "</div>";
        contentHtml += "</div>";

        firstCell.html(contentHtml);
    });

    $(".time-cell").unbind("click").click(function () {

        var bookingWindow = $("#roomBooking");
        if ($(this).hasClass("scheduled-cell")) {
            bookingWindow.show("modal");
            bookingWindow.data("event", $(this).data("event"));
            bookingWindow.find(".modal-title").text("Edit");
        } else {
            console.log("non-scheduled");
        }
    });
}