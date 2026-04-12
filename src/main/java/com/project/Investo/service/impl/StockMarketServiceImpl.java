package com.project.Investo.service.impl;

import com.project.Investo.model.StockList;
import com.project.Investo.repository.StockRepository;
import com.project.Investo.service.StockMarketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMarketServiceImpl implements StockMarketService {

    private final StockRepository stockRepository;

    public StockMarketServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<StockList> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public List<StockList> searchStocks(String keyword) {
        return stockRepository.findBySymbolContainingIgnoreCase(keyword);
    }
}