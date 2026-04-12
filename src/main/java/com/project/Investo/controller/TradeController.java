package com.project.Investo.controller;

import com.project.Investo.dto.request.TradeRequestDTO;
import com.project.Investo.model.Transaction;
import com.project.Investo.repository.TransactionRepository;
import com.project.Investo.security.entity.User;
import com.project.Investo.service.TradeService;
import com.project.Investo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trade")
public class TradeController {

    private final TradeService tradeService;
    private final UserService userService; // ✅ ADD
    private final TransactionRepository transactionRepository; // ✅ ADD

    // ✅ Constructor Injection FIXED
    public TradeController(TradeService tradeService,
                           UserService userService,
                           TransactionRepository transactionRepository) {
        this.tradeService = tradeService;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/buy")
    public String buyStock(@RequestBody Map<String, Object> body) {

        System.out.println("BODY: " + body);

        String symbol = (String) body.get("symbol");
        Integer quantity = (Integer) body.get("quantity");

        System.out.println("SYMBOL: " + symbol);
        System.out.println("QUANTITY: " + quantity);

        return tradeService.buyStock(symbol, quantity);
    }

    @PostMapping("/sell")
    public String sellStock(@RequestBody Map<String, Object> body) {

        String symbol = (String) body.get("symbol");
        Integer quantity = (Integer) body.get("quantity");

        return tradeService.sellStock(symbol, quantity);
    }

    @GetMapping("/history")
    public List<Transaction> getHistory() {
        User user = userService.getCurrentUser(); // ✅ now works
        return transactionRepository.findByUser(user); // ✅ now works
    }
}