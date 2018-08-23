var MINUTES_INDEXES = {
    0: 1,
    15: 2,
    30: 3,
    45: 4
};
var COLOR_MAPPING = {
    0: "#8b0000",
    1: "#ff9900",
    2: "#ffb02e",
    3: "#228b22",
    4: "#3b83bd",
    5: "#000080",
    6: "#4f0070"
};

$(document).ready(function () {

    $("#roomBooking").on("hide.bs.modal", function () {

        var selectPatient = $("#selectPatient");
        selectPatient.html("");
        var selectDoctor = $("#doctorName");
        selectDoctor.html("");
        $("#description").val("");
        $("#startHour").val(-1);
        $("#startMinute").val(-1);
        $("#endHour").val(-1);
        $("#endMinute").val(-1);

    });

    $("#roomBooking").on("show.bs.modal", function () {

        var isEdit = $(this).data("mode") === "EDIT";
        var eventId = $(this).data("event");
        // no needed for current application because we send all the data in previous call (Navigation.LOAD_TODAY)
        // but it not related to real world (in real world we plan to use something like short dto objects)
        // So, do loading by id because of it.

        $.post("/event/load/" + eventId, function (event) {

            $.post("/patient/load/available", function (patients) {

                patients.push(event.patient);
                patients.sort(function (a, b) {
                    return a.id - b.id;
                });
                var selectPatient = $("#selectPatient");
                selectPatient.html("");

                var html = "";
                var recipient = patients.filter(function (patient) {
                    return patient.id === event.patient.id;
                })[0];
                $.each(patients, function (index, patient) {
                    html += "<option value=" + patient.id + ">" + patient.name + "</option>";
                });
                selectPatient.html(html);
                selectPatient.val(recipient.id);

                $.post("/doctor/load/available", function (doctors) {

                    doctors.push(event.doctor);
                    doctors.sort(function (a, b) {
                        return a.id - b.id;
                    });

                    var selectDoctor = $("#doctorName");
                    selectDoctor.html("");

                    var html = "";
                    var selectedDoctor = doctors.filter(function (doctor) {
                        return doctor.id === event.doctor.id;
                    })[0];
                    $.each(doctors, function (index, doctor) {
                        html += "<option value=" + doctor.id + ">" + doctor.name + "</option>";
                    });
                    selectDoctor.html(html);
                    selectDoctor.val(selectedDoctor.id);

                    $("#description").val(event.description);

                    var startTime = new Date(event.plannedStartTime);
                    $("#startHour").val(startTime.getHours());
                    $("#startMinute").val(startTime.getMinutes());

                    if (event.estimatedEndTime && event.estimatedEndTime > 0) {
                        var endTime = new Date(event.estimatedEndTime);
                        $("#endHour").val(endTime.getHours());
                        $("#endMinute").val(endTime.getMinutes());
                    }
                });
            });
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

    $.each(positions, function (index, element) {

        var id = element.for;
        var event = element.item;
        var color = COLOR_MAPPING[id % 7];
        var eventId = event.id;
        var firstCell = $("td.time-cell[data-position=" + id + "_" + element.fromIdx + "]").css("background", color);
        firstCell.attr("data-event", eventId).attr("colspan", element.toIdx - element.fromIdx + 1).addClass("scheduled-cell");
        for (var i = element.fromIdx + 1; i <= element.toIdx; i++) {
            var position = id + "_" + i;
            $("td.time-cell[data-position=" + position + "]").remove();
        }
        var contentHtml = "<div>";
        contentHtml += "<div class='status-text'>" + event.status + "</div>";
        contentHtml += "<div class='description-text'>" + event.description + "</div>";
        contentHtml += "<div class='doctor-text'>" + event.doctor.name + "</div>";
        contentHtml += "<div class='patient-text'>" + event.patient.name + "</div>";
        contentHtml += "</div>";

        firstCell.html(contentHtml);
    });

    $(".time-cell").unbind("click").click(function () {

        var bookingWindow = $("#roomBooking");
        if ($(this).hasClass("scheduled-cell")) {

            bookingWindow.data("event", $(this).data("event"));
            bookingWindow.data("mode", "EDIT");
            bookingWindow.modal("show");
        } else {
            console.log("non-scheduled");
        }
    });
}