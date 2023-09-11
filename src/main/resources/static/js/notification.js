const eventSource = new EventSource('/api/notification/subscribe');

eventSource.onmessage = (event) => {
    const data = JSON.parse(event.data);

    // 브라우저 알림 요청 및 표시 로직
    if (Notification.permission === 'granted') {
        showNotification(data.title, data.body);
    } else if (Notification.permission !== 'denied') {
        Notification.requestPermission().then((permission) => {
            if (permission === 'granted') {
                showNotification(data.title, data.body);
            }
        });
    }
};

// 알림 표시
function showNotification(title, body) {
    if (Notification.permission === 'granted') {
        const options = {
            body,
            icon: 'path_to_icon.png',
        };
        const notification = new Notification(title, options);

        setTimeout(() => {
            notification.close();
        }, 3000);
    }
}
