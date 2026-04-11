package com.project.Investo.client;
import com.project.Investo.dto.AlphaVantageResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AlphaVantageClient {

    private final RestTemplate restTemplate;

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    public AlphaVantageClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AlphaVantageResponseDTO getStockQuote(String symbol) {

        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
                + symbol + "&apikey=" + apiKey;

        return restTemplate.getForObject(url, AlphaVantageResponseDTO.class);
    }
}