var MINUTES_INDEXES = {
    0: 1,
    15: 2,
    30: 3,
    45: 4
};

var MINUTES_OFFSET = {
    0: 0,
    1: 15,
    2: 30,
    3: 45
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

    $(".datepicker").datepicker();

    $("#newPatient").on("hide.bs.modal", function () {

        $("#name").val("");
        $("#sex").val(-1);
        $("#dayOfBirth").val("");
    });

    $("#roomBooking").on("hidden.bs.modal", function () {

        $("#selectPatient").html("");
        $("#doctorName").html("");
        $("#description").val("");
        $("#startHour").val("");
        $("#startMinute").val("");
        $("#endHour").val("");
        $("#endMinute").val("");

    });

    $("#roomBooking").on("show.bs.modal", function () {

        var isEdit = $(this).data("mode") === "EDIT";

        $.post("/patient/load/available", function (patients) {

            $.post("/doctor/load/available", function (doctors) {

                if (isEdit) {
                    var eventId = $("#roomBooking").data("event");
                    // no needed for current application because we send all the data in previous call (Navigation.LOAD_TODAY)
                    // but it not related to real world (in real world we plan to use something like short dto objects)
                    // So, do loading by id because of it.
                    $.post("/event/load/" + eventId, function (event) {
                        patients.push(event.patient);
                        doctors.push(event.doctor);

                        fillPatients(patients);
                        fillDoctors(doctors);

                        $("#selectPatient").val(event.patient.id);
                        $("#doctorName").val(event.doctor.id);
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
                } else {
                    fillPatients(patients);
                    fillDoctors(doctors);

                    var position = $("#roomBooking").data("position");
                    var time = parseInt(position.split("_")[1]);
                    var minute = (time - 1) % 4;
                    var hour = Math.floor((time - 1) / 4);
                    $("#startHour").val(hour);
                    $("#startMinute").val(MINUTES_OFFSET[minute]);
                }
            });
        });

    });

    function fillPatients(patients) {

        patients.sort(function (a, b) {
            return a.id - b.id;
        });
        var selectPatient = $("#selectPatient");
        selectPatient.html("");
        var html = "";
        html += "<option value='-1'></option>";
        $.each(patients, function (index, patient) {
            html += "<option value=" + patient.id + ">" + patient.name + "</option>";
        });
        selectPatient.html(html);
    }

    function fillDoctors(doctors) {

        doctors.sort(function (a, b) {
            return a.id - b.id;
        });
        var selectDoctor = $("#doctorName");
        selectDoctor.html("");
        var html = "";
        html += "<option value='-1'></option>";
        $.each(doctors, function (index, doctor) {
            html += "<option value=" + doctor.id + ">" + doctor.name + "</option>";
        });
        selectDoctor.html(html);
    }

    $("#saveBooking").click(function () {

        var event = {};
        var bookingWindow = $("#roomBooking");
        event.id = parseInt(bookingWindow.data("event"));
        var roomId = bookingWindow.data("room");
        var status = bookingWindow.data("status");
        event.status = status;

        var startDate = new Date();
        startDate.setHours($("#startHour").val());
        startDate.setMinutes($("#startMinute").val());
        startDate.setSeconds(0);
        startDate.setMilliseconds(0);
        event.plannedStartTime = startDate;

        var endHour = $("#endHour").val();
        var endMinute = $("#endMinute").val();
        if (endHour !== -1 && endMinute !== -1) {
            var endTime = new Date();
            endTime.setHours(endHour);
            endTime.setMinutes(endMinute);
            endTime.setSeconds(0);
            endTime.setMilliseconds(0);
            event.estimatedEndTime = endTime;
        }

        var doctor = {};
        var doctorSelect = $("#doctorName");
        doctor.id = doctorSelect.val();
        doctor.name = doctorSelect.find("option:selected").text();
        event.doctor = doctor;

        // no need full objects here
        // need only id and name
        var patient = {};
        var selectPatient = $("#selectPatient");
        patient.id = selectPatient.val();
        patient.name = selectPatient.find("option:selected").text();
        event.patient = patient;
        event.description = $("#description").val();

        var url = "/event/" + roomId + "/modify";
        if (bookingWindow.data("mode") === "NEW") {
            url = "/event/" + roomId + "/new";
        }

        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(event),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function () {
                bookingWindow.modal("hide");
            }
        });
    });

    $("#addPatient").click(function () {

        $("#newPatient").modal("show");
    });

    $("#savePatient").click(function () {

        var patient = {};
        patient.name = $("#name").val();
        patient.sex = $("#sex").val();
        patient.dayOfBirth = $("#dayOfBirth").val();

        $.ajax({
            url: "/patient/new",
            type: "POST",
            data: JSON.stringify(patient),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {

                var patients = $("#selectPatient option").map(function () {
                    return {id: $(this).val(), name: $(this).text()}
                });
                patients.push(response);
                patients.sort(function (a, b) {
                    return a.id - b.id;
                });

                var selectPatient = $("#selectPatient");
                selectPatient.html("");
                var html = "";
                $.each(patients, function (idx, el) {
                    html += "<option value=" + el.id + ">" + el.name + "</option>";
                });
                selectPatient.html(html);
                selectPatient.val(response.id);

                $("#newPatient").modal("hide");
            }
        });
    });

});

function draw(rows) {

    var thead = $(".schedule-table thead");
    thead.html("");
    var headHtml = "";
    headHtml += "<tr>";
    headHtml += "<th class='name-header'>Room Name</th>";
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
        contentHtml += "<div class='status-text' data-status=" + event.status + ">" + event.status + "</div>";
        contentHtml += "<div class='description-text'>" + event.description + "</div>";
        contentHtml += "<div class='doctor-text'>" + event.doctor.name + "</div>";
        contentHtml += "<div class='patient-text'>" + event.patient.name + "</div>";
        contentHtml += "</div>";

        firstCell.html(contentHtml);
    });

    $(".time-cell").unbind("click").click(function () {

        var bookingWindow = $("#roomBooking");
        bookingWindow.data("room", $(this).parent().data("id"));

        if ($(this).hasClass("scheduled-cell")) {

            bookingWindow.data("event", $(this).data("event"));
            bookingWindow.data("status", $(this).find(".status-text").data("status"));
            bookingWindow.data("mode", "EDIT");
        } else {
            bookingWindow.data("mode", "NEW");
        }
        bookingWindow.data("position", $(this).data("position"));
        bookingWindow.modal("show");
    });
}