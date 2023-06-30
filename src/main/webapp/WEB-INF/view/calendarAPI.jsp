<!doctype html>
<html lang="ko">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
</head>
<body>
   <div id="calendar"></div>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            heigth: '200',
            eventLimit: 3,
            eventLimitText: '더보기',
            eventLimitClick: 'popover',
            timeZone: 'ko',
            slotDuration: '01:00',
            minTime: '09:00:00',
            maxTime: '19:00:00',
            customButtons: {
                addEventButton: {
                    text: '상담 예약 등록하기',
                    click: function () {
                        reSetData();
                        setRegUptDelButton("reg");
                        layer_popup("#layer1", "", "", "readonly");
                    }
                },
                setMonthDefault: {
                    text: '월 셋팅',
                    click: function () {
                        setCalendarDefault("M");
                    }
                },
                setDayDefault: {
                    text: '일 셋팅',
                    click: function () {
                        setCalendarDefault("D");
                    }
                },
                prev: {
                    click: function () {
                        calendar.prev();
                        //setJqGrid();
                    }
                },
                next: {
                    click: function () {
                        calendar.next();
                        //setJqGrid();
                    }
                }

            },
            header: {
                left: 'prev, next ,today',
                center: 'title',
                right: 'addEventButton, dayGridMonth, timeGridWeek, timeGridDay'
            },
            footer: {
                right: 'setMonthDefault, setDayDefault'
            },
            buttonText: {
                today: '오늘',
                month: '월',
                week: '주',
                day: '일'
            },
            editable: false,
            droppable: false,
            defaultView: 'dayGridMonth',
            locales: 'ko',

            // Create new event
            dateClick: function (info) {
                console.log(info)
                Swal.fire({
                    html: "<div class='mb-7'>Create new event?</div><div class='fw-bold mb-5'>Event Name:</div><input type='text' class='form-control' name='event_name' />",
                    icon: "info",
                    showCancelButton: true,
                    buttonsStyling: false,
                    confirmButtonText: "Yes, create it!",
                    cancelButtonText: "No, return",
                    customClass: {
                        confirmButton: "btn btn-primary",
                        cancelButton: "btn btn-active-light"
                    }
                }).then(function (result) {
                    if (result.value) {
                        var title = document.querySelector("input[name=;event_name']").value;
                        if (title) {
                            calendar.addEvent({
                                title: title,
                                start: arg.start,
                                end: arg.end,
                                allDay: arg.allDay
                            })
                        }
                        calendar.unselect()
                    } else if (result.dismiss === "cancel") {
                        Swal.fire({
                            text: "Event creation was declined!.",
                            icon: "error",
                            buttonsStyling: false,
                            confirmButtonText: "Ok, got it!",
                            customClass: {
                                confirmButton: "btn btn-primary",
                            }
                        });
                    }
                });
            },

            // Delete event
            eventClick: function (arg) {
                eventClickData(arg, "1", "update");//켈린더 이벤트 클릭시
            },
            events: []
        });
        calendar.render();
    })
</script>
</html>