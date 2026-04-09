package com.project.Investo.service.impl;


import com.project.Investo.client.AlphaVantageClient;
import com.project.Investo.dto.AlphaVantageResponseDTO;
import com.project.Investo.dto.StockResponseDTO;
import com.project.Investo.service.StockService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final AlphaVantageClient alphaVantageClient;

    public StockServiceImpl(AlphaVantageClient alphaVantageClient) {
        this.alphaVantageClient = alphaVantageClient;
    }

    @Override
    public StockResponseDTO getStockBySymbol(String symbol) {

        AlphaVantageResponseDTO response = alphaVantageClient.getStockQuote(symbol);

        String apiSymbol = response.getGlobalQuote().getSymbol();
        String priceStr = response.getGlobalQuote().getPrice();

        double price = Double.parseDouble(priceStr);

        return new StockResponseDTO(apiSymbol, apiSymbol, price);
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
