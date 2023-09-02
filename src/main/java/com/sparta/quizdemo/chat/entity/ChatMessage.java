package com.sparta.quizdemo.chat.entity;

import com.sparta.quizdemo.chat.dto.ChatMessageRequestDto;
import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chatMessage")
public class ChatMessage extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column
    private String roomId;

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @Column
    private String message;

    public ChatMessage(ChatMessageRequestDto requestDto, ChatRoom chatRoom, User user) {
        this.chatRoom = chatRoom;
        this.roomId = chatRoom.getRoomId();
        this.username = requestDto.getUsername();
        this.user = user;
        this.message = requestDto.getMessage();
    }
}
