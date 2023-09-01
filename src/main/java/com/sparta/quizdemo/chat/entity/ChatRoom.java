package com.sparta.quizdemo.chat.entity;

import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chatRoom")
public class ChatRoom extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;

    @Column
    private String username;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true)
    private String roomId;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatRoom(User user, String roomId) {
        this.roomId = roomId;
        this.user = user;
    }
}