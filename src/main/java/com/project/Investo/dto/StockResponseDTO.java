package com.project.Investo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponseDTO {

    private String symbol;
    private String name;
    private double price;
}