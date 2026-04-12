package com.project.Investo.dto.request;


import lombok.Data;

@Data
public class TradeRequestDTO {

    private String stockSymbol;
    private int quantity;
}