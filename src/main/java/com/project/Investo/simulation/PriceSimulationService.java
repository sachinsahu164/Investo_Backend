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

    // ✅ Initialize price (ONLY ONCE)
    public void setInitialPrice(String symbol, double price) {
        symbol = normalize(symbol);

        realPrices.put(symbol, price);
        displayPrices.put(symbol, price);
    }

    // ✅ Get simulated price
    public double getSimulatedPrice(String symbol) {
        symbol = normalize(symbol);

        return displayPrices.getOrDefault(symbol, 0.0);
    }

    // ✅ Update real price (optional future use)
    public void updateRealPrice(String symbol, double newPrice) {
        symbol = normalize(symbol);

        realPrices.put(symbol, newPrice);
    }

    // ✅ Single stock simulation
    public void simulatePrice(String symbol) {
        symbol = normalize(symbol);

        double currentDisplay = displayPrices.getOrDefault(symbol, 0.0);
        double realPrice = realPrices.getOrDefault(symbol, currentDisplay);

        if (currentDisplay == 0.0) return; // safety

        // 🔥 Random fluctuation (-0.5% to +0.5%)
        double changePercent = (random.nextDouble() - 0.5) * 0.01;
        double randomChange = currentDisplay * changePercent;

        // 🔥 Smooth correction towards real price
        double diff = realPrice - currentDisplay;
        double correction = diff * 0.05;

        double newPrice = currentDisplay + randomChange + correction;

        displayPrices.put(symbol, newPrice);
    }

    // ✅ Simulate all
    public void simulateAllPrices() {
        for (String symbol : displayPrices.keySet()) {
            simulatePrice(symbol);
        }
    }

    public Map<String, Double> getAllPrices() {
        return displayPrices;
    }

    // 🔥 IMPORTANT (symbol consistency fix)
    private String normalize(String symbol) {
        return symbol == null ? "" : symbol.trim().toUpperCase();
    }
}