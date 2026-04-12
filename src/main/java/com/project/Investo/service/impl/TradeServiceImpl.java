package com.project.Investo.service.impl;

import com.project.Investo.model.Portfolio;
import com.project.Investo.model.Transaction;
import com.project.Investo.repository.PortfolioRepository;
import com.project.Investo.repository.TransactionRepository;
import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.service.TradeService;
import com.project.Investo.service.UserService;
import com.project.Investo.simulation.PriceSimulationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private final UserService userService;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final PriceSimulationService simulationService;
    private final TransactionRepository transactionRepository;

    public TradeServiceImpl(UserService userService,
                            PortfolioRepository portfolioRepository,
                            UserRepository userRepository,
                            PriceSimulationService simulationService,
                            TransactionRepository transactionRepository) {
        this.userService = userService;
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.simulationService = simulationService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String buyStock(String symbol, int quantity) {

        User user = userService.getCurrentUser();

        double price = simulationService.getSimulatedPrice(symbol);

        if (price == 0.0) {
            throw new RuntimeException("Stock price not available");
        }

        double totalCost = price * quantity;

        if (user.getBalance() < totalCost) {
            throw new RuntimeException("Insufficient balance");
        }

        // 🔥 FIX: EMAIL BASED SEARCH
        Optional<Portfolio> optionalPortfolio =
                portfolioRepository.findByUserEmailAndStockSymbol(user.getEmail(), symbol);

        if (optionalPortfolio.isPresent()) {

            Portfolio portfolio = optionalPortfolio.get();

            int oldQty = portfolio.getQuantity();
            double oldAvg = portfolio.getAvgPrice();

            int newQty = oldQty + quantity;

            double newAvg = ((oldAvg * oldQty) + (price * quantity)) / newQty;

            portfolio.setQuantity(newQty);
            portfolio.setAvgPrice(newAvg);

            portfolioRepository.save(portfolio);

        } else {

            Portfolio portfolio = new Portfolio();
            portfolio.setUser(user);
            portfolio.setStockSymbol(symbol);
            portfolio.setQuantity(quantity);
            portfolio.setAvgPrice(price);

            portfolioRepository.save(portfolio);
        }

        user.setBalance(user.getBalance() - totalCost);
        userRepository.save(user);

        Transaction txn = new Transaction();
        txn.setUser(user);
        txn.setStockSymbol(symbol);
        txn.setType("BUY");
        txn.setPrice(price);
        txn.setQuantity(quantity);
        txn.setTimestamp(LocalDateTime.now());

        transactionRepository.save(txn);

        return "Stock bought successfully 🚀";
    }

    @Override
    public String sellStock(String symbol, int quantity) {

        User user = userService.getCurrentUser();

        // 🔥 FIX: EMAIL BASED SEARCH
        Portfolio portfolio = portfolioRepository
                .findByUserEmailAndStockSymbol(user.getEmail(), symbol)
                .orElseThrow(() -> new RuntimeException("Stock not owned"));

        if (portfolio.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock to sell");
        }

        double price = simulationService.getSimulatedPrice(symbol);

        double totalValue = price * quantity;

        int remainingQty = portfolio.getQuantity() - quantity;

        if (remainingQty == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolio.setQuantity(remainingQty);
            portfolioRepository.save(portfolio);
        }

        user.setBalance(user.getBalance() + totalValue);
        userRepository.save(user);

        Transaction txn = new Transaction();
        txn.setUser(user);
        txn.setStockSymbol(symbol);
        txn.setType("SELL");
        txn.setPrice(price);
        txn.setQuantity(quantity);
        txn.setTimestamp(LocalDateTime.now());

        transactionRepository.save(txn);

        return "Stock sold successfully 💥";
    }
}