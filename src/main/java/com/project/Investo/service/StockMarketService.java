package com.project.Investo.service;

import com.project.Investo.model.StockList;

import java.util.List;

public interface StockMarketService {

    List<StockList> getAllStocks();

    List<StockList> searchStocks(String keyword);
}