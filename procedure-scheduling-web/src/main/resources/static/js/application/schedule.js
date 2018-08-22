var MINUTES_INDEXES = {
    0: 1,
    15: 2,
    30: 3,
    45: 4
};

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

        html += "<tr data-id=" + element.room.id + ">";
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
                event: item.id,
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
    tbody.html(html);

    $.each(positions, function (index, element) {

        var id = element.for;
        for (var i = element.fromIdx; i <= element.toIdx; i++) {
            $("td.time-cell[data-position=" + id + "_" + i + "]").addClass("scheduled-cell").attr("data-event", element.event);
        }
    });

    $("td.time-cell.scheduled-cell").unbind("mouseenter mouseleave").hover(function () {

        $(this).parent().find(".scheduled-cell[data-event=" + $(this).data("event") + "]").addClass("hovered-scheduled-cell");

    }, function () {

        $(this).parent().find(".scheduled-cell[data-event=" + $(this).data("event") + "]").removeClass("hovered-scheduled-cell");
    });
}