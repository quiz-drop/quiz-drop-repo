package com.sparta.quizdemo.sse.entity;

import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notification")
public class Notification extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 알림 내용 */
    @Column
    private String content;

    /* 읽음 여부 */
    @Column(nullable = false)
    private Boolean isRead;

    /* 알림 종류 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User receiver;

    @Builder
    public Notification(User receiver, NotificationType notificationType, String content, Boolean isRead) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = content;
        this.isRead = isRead;
    }

    public void read() {
        isRead = true;
    }
}
