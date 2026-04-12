package com.project.Investo.repository;

import com.project.Investo.model.StockList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<StockList, Long> {

    List<StockList> findBySymbolContainingIgnoreCase(String keyword);
}