package com.project.Investo.service.impl;

import com.project.Investo.dto.response.PortfolioResponseDTO;
import com.project.Investo.model.Portfolio;

import com.project.Investo.repository.PortfolioRepository;
import com.project.Investo.service.PortfolioService;
import com.project.Investo.service.UserService;
import com.project.Investo.simulation.PriceSimulationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserService userService;
    private final PriceSimulationService simulationService;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository,
                                UserService userService,
                                PriceSimulationService simulationService) {
        this.portfolioRepository = portfolioRepository;
        this.userService = userService;
        this.simulationService = simulationService;
    }

    @Override
    public List<PortfolioResponseDTO> getUserPortfolio() {

        String email = userService.getCurrentUser().getEmail();

        List<Portfolio> portfolios =
                portfolioRepository.findByUserEmail(email);

        return portfolios.stream().map(p -> {

            double currentPrice =
                    simulationService.getSimulatedPrice(p.getStockSymbol());

            // ✅ PROFIT / LOSS CALCULATION
            double profitLoss =
                    (currentPrice - p.getAvgPrice()) * p.getQuantity();

            return new PortfolioResponseDTO(
                    p.getStockSymbol(),
                    p.getQuantity(),
                    p.getAvgPrice(),
                    currentPrice,
                    profitLoss
            );

        }).collect(Collectors.toList());
    }
}