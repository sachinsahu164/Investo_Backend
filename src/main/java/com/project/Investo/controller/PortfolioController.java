package com.project.Investo.controller;


import com.project.Investo.dto.response.PortfolioResponseDTO;
import com.project.Investo.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public List<PortfolioResponseDTO> getPortfolio() {
        return portfolioService.getUserPortfolio();
    }
}