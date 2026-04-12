package com.project.Investo.dto.response;

import lombok.Data;

@Data
public class PortfolioResponseDTO {

    private String stockSymbol;
    private int quantity;
    private double avgPrice;
    private double currentPrice;
    private double profitLoss;
}