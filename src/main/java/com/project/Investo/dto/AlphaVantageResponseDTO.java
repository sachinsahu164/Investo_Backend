package com.project.Investo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AlphaVantageResponseDTO {

    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

    @Data
    public static class GlobalQuote {

        @JsonProperty("01. symbol")
        private String symbol;

        @JsonProperty("05. price")
        private String price;
    }
}