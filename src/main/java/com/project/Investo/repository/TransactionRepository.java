package com.project.Investo.repository;

import com.project.Investo.model.Transaction;
import com.project.Investo.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);
}