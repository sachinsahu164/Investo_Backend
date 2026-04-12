package com.project.Investo.simulation;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PriceSimulationService {

    private final Map<String, Double> realPrices = new ConcurrentHashMap<>();
    private final Map<String, Double> displayPrices = new ConcurrentHashMap<>();

    private final Random random = new Random();

    // Initialize price
    public void setInitialPrice(String symbol, double price) {
        realPrices.put(symbol, price);
        displayPrices.put(symbol, price);
    }

    // Get simulated price
    public double getSimulatedPrice(String symbol) {

        if (symbol == null || symbol.isEmpty()) {
            throw new RuntimeException("Symbol is NULL from request ❌");
        }

        return displayPrices.getOrDefault(symbol, 0.0);
    }

    // Update real price (API se)
    public void updateRealPrice(String symbol, double newPrice) {
        realPrices.put(symbol, newPrice);
    }

    // Run simulation (call every second)
    public void simulatePrice(String symbol) {

        double currentDisplay = displayPrices.getOrDefault(symbol, 0.0);
        double realPrice = realPrices.getOrDefault(symbol, currentDisplay);

        // Random fluctuation (-1% to +1%)
        double changePercent = (random.nextDouble() - 0.5) * 0.02;
        double randomChange = currentDisplay * changePercent;

        // Move towards real price
        double diff = realPrice - currentDisplay;
        double correction = diff * 0.1; // smooth adjust

        double newPrice = currentDisplay + randomChange + correction;

        displayPrices.put(symbol, newPrice);
    }
    public void simulateAllPrices() {

        for (String symbol : displayPrices.keySet()) {
            simulatePrice(symbol);
        }
    }
    public Map<String, Double> getAllPrices() {
        return displayPrices;
    }
}
