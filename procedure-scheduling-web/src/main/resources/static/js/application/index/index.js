$(document).ready(function () {

    var rooms;

    var timetable = new Timetable();
    timetable.setScope(0, 23);

    var socket = new SockJS("/ws/channel");
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/common", function (response) {
            var event = JSON.parse(response.body);
            if (event === "ROOMS_UPDATE") {
                console.log("Reload table...");
            }
        });
    });

    $.post("/room/load/all", function (rooms) {

        var roomNames = rooms.map(function (room) {
            return room.name;
        });
        timetable.addLocations(roomNames);

        $.post("/study/load/all", function (studies) {

            console.log(studies);

            $.each(studies, function (sidx, study) {

                timetable.addEvent(study.description, study.room.name, new Date(study.plannedStartTime), new Date(study.estimatedEndTime));
            });

            var renderer = new Timetable.Renderer(timetable);
            renderer.draw('.timetable');
        });
    });

});
