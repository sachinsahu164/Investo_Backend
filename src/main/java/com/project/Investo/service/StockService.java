package com.project.Investo.service;



import com.project.Investo.dto.StockResponseDTO;

import java.util.List;

public interface StockService {

    StockResponseDTO getStockBySymbol(String symbol);

    List<StockResponseDTO> getAllStocks();
}