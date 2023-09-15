document.addEventListener("DOMContentLoaded", function() {
    let eventSource = new EventSource("/api/notifications/subscribe");

    // SSE 이벤트 핸들러
    eventSource.onmessage = function (event) {
        const notification = JSON.parse(event.data);

        // 알림을 notification-list에 추가
        const notificationList = document.getElementById("notification-list");
        if (notificationList) {
            const listItem = document.createElement("li");
            listItem.textContent = notification.content;
            notificationList.appendChild(listItem);
        } else {
            console.error("Element with ID 'notification-list' not found.");
        }
    };

    // 서버에서 연결이 닫히는 이벤트를 받으면 EventSource를 닫습니다.
    eventSource.addEventListener("server-close", function() {
        eventSource.close();
        eventSource = null;
    });
});

function checkAuthorizationCookie() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();

        // "Authorization" 쿠키가 존재하는 경우 true 반환
        if (cookie.startsWith("Authorization")) {
            return true;
        }
    }
    // "Authorization" 쿠키가 존재하지 않는 경우 false 반환
    return false;
}

function displayNotifications() {
    // 알림 데이터를 서버에서 가져옵니다.
    $.ajax({
        url: "/api/notifications/", // 알림 데이터를 가져오는 엔드포인트 URL 설정
        type: "GET",
        contentType: "application/json",
        success: function (notifications) {
            // 알림 데이터를 받아와서 표시할 위치를 선택합니다.
            const notificationList = document.getElementById("notification-list");

            // 알림 리스트를 초기화합니다.
            notificationList.innerHTML = "";

            // 알림 데이터를 돌면서 리스트 아이템을 생성합니다.
            notifications.forEach(function (notification) {
                const listItem = document.createElement("li");
                listItem.textContent = notification.content + " (" + notification.createdAt + ")";
                notificationList.appendChild(listItem);
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 오류 처리
            console.error("AJAX Error:", textStatus, errorThrown);
        }
    });
}

// 페이지 로드 시 실행
$(document).ready(function () {
    // 쿠키를 확인하고, 쿠키가 존재할 때만 알림 리스트를 표시합니다.
    if (checkAuthorizationCookie()) {
        displayNotifications();
    }
});


// 알림 전체 삭제 함수
function deleteAllNotifications() {
    $.ajax({
        url: "/api/notifications/delete",
        type: "DELETE",
        success: function (response) {
            reloadPage();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // 오류 처리
            console.error("AJAX Error:", textStatus, errorThrown);
        }
    });
}

function reloadPage() {
    location.reload();
}

// "알림 전체 삭제" 버튼 클릭 시 삭제 함수 호출
$(document).ready(function () {
    const deleteAllBtn = document.getElementById("delete-all-btn");
    if (deleteAllBtn) {
        deleteAllBtn.addEventListener("click", function () {
            deleteAllNotifications();
        });
    }
});