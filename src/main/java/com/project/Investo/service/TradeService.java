package com.project.Investo.service;

public interface TradeService {

    String buyStock(String symbol, int quantity);

    // ✅ 🔥 SELL STOCK METHOD (Merged Properly)
    String sellStock(String symbol, int quantity);
}
