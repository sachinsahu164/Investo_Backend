package com.project.Investo.service;

import com.project.Investo.dto.StockDetailDTO;
import com.project.Investo.model.StockList;

import java.util.List;

public interface StockMarketService {
    StockDetailDTO getStockDetail(String symbol);
    List<StockList> getAllStocks();

    List<StockList> searchStocks(String keyword);
}