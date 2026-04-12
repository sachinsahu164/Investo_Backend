package com.project.Investo.controller;

import com.project.Investo.model.StockList;
import com.project.Investo.service.StockMarketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
public class StockMarketController {

    private final StockMarketService stockService;

    public StockMarketController(StockMarketService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks")
    public List<StockList> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/search")
    public List<StockList> searchStocks(@RequestParam String keyword) {
        return stockService.searchStocks(keyword);
    }
}