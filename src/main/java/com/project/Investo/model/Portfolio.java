package com.project.Investo.model;



import com.project.Investo.security.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 User relation
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String stockSymbol;

    private int quantity;

    private double avgPrice;
}