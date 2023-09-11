package com.sparta.quizdemo.backoffice.entity;

import com.sparta.quizdemo.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "black_email")
public class BlackEmail extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String blackEmail;

    public BlackEmail(String email) {
        this.blackEmail = email;
    }
}
