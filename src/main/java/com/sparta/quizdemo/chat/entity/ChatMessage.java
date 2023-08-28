package com.sparta.quizdemo.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.user.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chatMessage")
@NoArgsConstructor
public class ChatMessage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column
    private String roomId;

    @Column
    private String sender;

    private String message;

    private UserRoleEnum role;

    @Column
    @JsonFormat(pattern = "yy-MM-dd HH:mm")
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    public enum MessageType {
        ENTER, TALK
    }
}
