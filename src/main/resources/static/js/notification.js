// // const eventSource = new EventSource('/api/notification/subscribe');
// //
// // eventSource.onmessage = (event) => {
// //     const data = JSON.parse(event.data);
// //
// //     // 브라우저 알림 요청 및 표시 로직
// //     if (Notification.permission === 'granted') {
// //         showNotification(data.title, data.body);
// //     } else if (Notification.permission !== 'denied') {
// //         Notification.requestPermission().then((permission) => {
// //             if (permission === 'granted') {
// //                 showNotification(data.title, data.body);
// //             }
// //         });
// //     }
// // };
// //
// // // 알림 표시
// // function showNotification(title, body) {
// //     if (Notification.permission === 'granted') {
// //         const options = {
// //             body,
// //             icon: 'path_to_icon.png',
// //         };
// //         const notification = new Notification(title, options);
// //
// //         setTimeout(() => {
// //             notification.close();
// //         }, 3000);
// //     }
// // }
//
// $(document).ready(function () {
//     // 페이지가 로드될 때 알림을 로드합니다.
//     loadNotifications();
//
//     // 실시간 알림을 구독합니다.
//     const eventSource = new EventSource('/api/notifications/subscribe');
//
//     eventSource.onmessage = function (event) {
//         const notification = JSON.parse(event.data);
//         displayNotification(notification);
//     };
//
//     // 알림 전체 삭제 버튼을 클릭할 때의 처리
//     $('#delete-all-btn').click(function () {
//         deleteAllNotifications();
//     });
//
//     // 알림을 로드하는 함수
//     function loadNotifications() {
//         $.get('/api/notifications', function (notifications) {
//             notifications.forEach(function (notification) {
//                 displayNotification(notification);
//             });
//         });
//     }
//
//     // 알림을 화면에 표시하는 함수
//     function displayNotification(notification) {
//         const notificationList = $('#notification-list');
//         const listItem = `<li>${notification.message} <button class="btn btn-success read-btn" data-notification-id="${notification.id}">읽음</button></li>`;
//         notificationList.append(listItem);
//
//         // "읽음으로 표시" 버튼 클릭 처리
//         $(`.read-btn[data-notification-id="${notification.id}"]`).click(function () {
//             markNotificationAsRead(notification.id);
//             $(this).attr('disabled', true);
//         });
//     }
//
//     // 알림을 "읽음"으로 표시하는 함수
//     function markNotificationAsRead(notificationId) {
//         $.post(`/api/notifications/read/${notificationId}`);
//     }
//
//     // 모든 알림을 삭제하는 함수
//     function deleteAllNotifications() {
//         $.ajax({
//             url: '/api/notifications/delete',
//             type: 'DELETE',
//             success: function () {
//                 $('#notification-list').empty(); // 알림 목록을 비웁니다.
//             }
//         });
//     }
// });
//
// function addNotificationToSidebar(message) {
//     const notificationList = $('#notification-list');
//     const listItem = `<li>${message}</li>`;
//     notificationList.prepend(listItem); // 새로운 알림을 리스트 맨 위에 추가
// }
//
// function completeOrder() {
//
//     const orderMessage = "주문이 완료되었습니다!";
//     console.log(orderMessage)
//     addNotificationToSidebar(orderMessage);
// }
//
// // 알림 목록을 비동기적으로 가져와서 페이지에 추가하는 함수
// function loadNotifications() {
//     $.ajax({
//         url: 'api/notifications', // 알림 목록을 가져오는 엔드포인트 URL을 여기에 넣으세요.
//         method: 'GET',
//         dataType: 'json',
//         success: function (notifications) {
//             // 알림 목록을 받아서 처리합니다.
//             const notificationList = $('#notification-list');
//             notificationList.empty(); // 기존 알림 목록을 비웁니다.
//
//             notifications.forEach(function (notification) {
//                 // 각 알림 항목을 생성하여 목록에 추가합니다.
//                 const listItem = $('<li></li>');
//                 listItem.text(notification.title + ': ' + notification.content);
//                 notificationList.append(listItem);
//             });
//         },
//         error: function (xhr, status, error) {
//             console.error('알림 목록을 불러오는 중 오류가 발생했습니다.');
//         }
//     });
// }
//
// // 페이지 로드 시 알림 목록을 가져와서 표시합니다.
// loadNotifications();
//
// // 알림 전체 삭제 버튼 클릭 이벤트 처리
// // 알림 전체 삭제 버튼 클릭 이벤트 처리
// $('#delete-all-btn').click(function () {
//     // 알림을 모두 삭제하는 비동기 요청을 보내세요.
//     $.ajax({
//         url: 'api/notifications/delete', // 알림 전체 삭제 엔드포인트 URL을 여기에 넣으세요.
//         method: 'DELETE',
//         success: function () {
//             // 성공적으로 삭제되면 화면에서도 삭제합니다.
//             $('#notification-list').empty();
//         },
//         error: function (xhr, status, error) {
//             console.error('알림을 삭제하는 중 오류가 발생했습니다.');
//         }
//     });
// });
