package com.project.Investo.service.impl;

import com.project.Investo.client.AlphaVantageClient;
import com.project.Investo.dto.AlphaVantageResponseDTO;
import com.project.Investo.dto.StockResponseDTO;
import com.project.Investo.service.StockService;
import com.project.Investo.simulation.PriceSimulationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final AlphaVantageClient alphaVantageClient;
    private final PriceSimulationService simulationService;

    public StockServiceImpl(AlphaVantageClient alphaVantageClient,
                            PriceSimulationService simulationService) {
        this.alphaVantageClient = alphaVantageClient;
        this.simulationService = simulationService;
    }

    @Override
    public StockResponseDTO getStockBySymbol(String symbol) {

        AlphaVantageResponseDTO response = alphaVantageClient.getStockQuote(symbol);

        // 🔥 Safety check
        if (response == null || response.getGlobalQuote() == null) {
            throw new RuntimeException("API error or limit reached");
        }

        String apiSymbol = response.getGlobalQuote().getSymbol();
        String priceStr = response.getGlobalQuote().getPrice();

        double realPrice = Double.parseDouble(priceStr);

        // 🔥 Step 1: Update real price from API
        simulationService.updateRealPrice(apiSymbol, realPrice);

        // 🔥 Step 2: Initialize simulation (first time)
        if (simulationService.getSimulatedPrice(apiSymbol) == 0.0) {
            simulationService.setInitialPrice(apiSymbol, realPrice);
        }

        // 🔥 Step 3: Get simulated price
        double simulatedPrice = simulationService.getSimulatedPrice(apiSymbol);

        return new StockResponseDTO(apiSymbol, apiSymbol, simulatedPrice);
    }

    @Override
    public List<StockResponseDTO> getAllStocks() {

        List<String> symbols = List.of(
                "RELIANCE.BSE",
                "TCS.BSE",
                "INFY.BSE"
        );

        List<StockResponseDTO> stockList = new ArrayList<>();

        for (String symbol : symbols) {
            stockList.add(getStockBySymbol(symbol));
        }

        return stockList;
    }
}