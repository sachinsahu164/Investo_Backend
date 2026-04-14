package com.project.Investo.service.impl;

import com.project.Investo.dto.AlphaVantageResponseDTO;
import com.project.Investo.dto.StockDetailDTO;
import com.project.Investo.model.StockList;
import com.project.Investo.repository.StockRepository;
import com.project.Investo.service.StockMarketService;
import com.project.Investo.simulation.PriceSimulationService;
import org.springframework.stereotype.Service;
import com.project.Investo.client.AlphaVantageClient;

import java.util.List;

@Service
public class StockMarketServiceImpl implements StockMarketService {

    private final StockRepository stockRepository;
    private final PriceSimulationService simulationService;
    private final AlphaVantageClient alphaVantageClient;

    public StockMarketServiceImpl(StockRepository stockRepository,
                                  PriceSimulationService simulationService,
                                  AlphaVantageClient alphaVantageClient) {
        this.stockRepository = stockRepository;
        this.simulationService = simulationService;
        this.alphaVantageClient = alphaVantageClient;
    }

    @Override
    public List<StockList> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public List<StockList> searchStocks(String keyword) {
        return stockRepository.findBySymbolContainingIgnoreCase(keyword);
    }

    @Override
    public StockDetailDTO getStockDetail(String symbol) {

        StockList stock = stockRepository.findAll()
                .stream()
                .filter(s -> s.getSymbol().equalsIgnoreCase(symbol))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        // 🔥 STEP 1: Get real price from API
        AlphaVantageResponseDTO response =
                alphaVantageClient.getStockQuote(symbol + ".BSE");

        double realPrice = Double.parseDouble(
                response.getGlobalQuote().getPrice()
        );

        // 🔥 STEP 2: Initialize simulation ONLY ON FIRST CALL
        if (simulationService.getSimulatedPrice(symbol) == 0.0) {
            simulationService.setInitialPrice(symbol, realPrice);
        }

        // 🔥 STEP 3: Always update real price in background
        simulationService.updateRealPrice(symbol, realPrice);

        // 🔥 STEP 4: USE SIMULATION PRICE (NOT REAL PRICE DIRECTLY)
        double price = simulationService.getSimulatedPrice(symbol);

        // optional fake change for UI
        double change = realPrice - price;
        double changePercent = (change / realPrice) * 100;

        return new StockDetailDTO(
                stock.getSymbol(),
                stock.getName(),
                price,
                change,
                changePercent
        );
    }
}