$(document).ready(function () {

    var socket = new SockJS("/ws/channel");
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/common", function (response) {
            var event = JSON.parse(response.body);
            if (event === "ROOMS_UPDATE") {
                redraw();
            }
        });
    });

    redraw();
});

function redraw() {
    $.post("/event/load/today", function (events) {

        draw(events);
    });
}
