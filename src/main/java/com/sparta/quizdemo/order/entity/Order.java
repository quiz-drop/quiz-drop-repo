package com.sparta.quizdemo.order.entity;

import com.sparta.quizdemo.common.entity.TimeStamped;
import com.sparta.quizdemo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "totalPrice", nullable = false)
    private Long totalPrice;

    @Column(name = "completeTime", nullable = false)
    private LocalDateTime completeTime;

    @Column(name = "request")
    private String request;

    @Column(name = "delivery")
    private Boolean delivery;

    @Setter
    @Column(name = "orderComplete")
    private Boolean orderComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private String username;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order(User user, Long totalPrice, LocalDateTime completeTime, Boolean delivery, String request, Boolean orderComplete) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.completeTime = completeTime;
        this.request = request;
        this.delivery = delivery;
        this.orderComplete = orderComplete;
    }
}
