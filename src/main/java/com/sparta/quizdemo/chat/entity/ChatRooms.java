package com.sparta.quizdemo.chat.entity;

import com.sparta.quizdemo.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chatRooms")
public class ChatRooms extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;

    @Column(unique = true)
    private String roomId;

    // 채팅방 삭제시 메시지, 채팅유저 삭제
    @OneToMany(mappedBy = "chatRooms", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserChatRooms> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "chatRooms", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<ChatMessages> chatMessages = new ArrayList<>();
}