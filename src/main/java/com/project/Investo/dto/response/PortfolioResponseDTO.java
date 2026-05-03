package com.project.Investo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponseDTO {

    private String stockSymbol;
    private int quantity;
    private double avgPrice;
    private double currentPrice;
    private double profitLoss;
}