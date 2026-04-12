package com.project.Investo.model;

import com.project.Investo.security.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String stockSymbol;

    private String type; // BUY / SELL

    private double price;

    private int quantity;

    private LocalDateTime timestamp;
}
