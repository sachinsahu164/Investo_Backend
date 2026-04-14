package com.project.Investo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDetailDTO {

    private String symbol;
    private String name;
    private double price;
    private double change;
    private double changePercent;
}