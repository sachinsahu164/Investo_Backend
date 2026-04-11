package com.project.Investo.controller;


import com.project.Investo.dto.StockResponseDTO;
import com.project.Investo.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Get single stock
    @GetMapping("/{symbol}")
    public StockResponseDTO getStock(@PathVariable String symbol) {
        return stockService.getStockBySymbol(symbol);
    }

    // Get all stocks
    @GetMapping
    public List<StockResponseDTO> getAllStocks() {
        return stockService.getAllStocks();
    }
}