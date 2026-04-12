package com.project.Investo.service.impl;


import com.project.Investo.dto.response.PortfolioResponseDTO;
import com.project.Investo.model.Portfolio;
import com.project.Investo.repository.PortfolioRepository;
import com.project.Investo.security.entity.User;
import com.project.Investo.service.PortfolioService;
import com.project.Investo.service.UserService;
import com.project.Investo.simulation.PriceSimulationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        User user = userService.getCurrentUser();

        List<Portfolio> portfolioList = portfolioRepository.findByUser(user);

        List<PortfolioResponseDTO> responseList = new ArrayList<>();

        for (Portfolio p : portfolioList) {

            double currentPrice = simulationService.getSimulatedPrice(p.getStockSymbol());

            double profitLoss =
                    (currentPrice - p.getAvgPrice()) * p.getQuantity();

            PortfolioResponseDTO dto = new PortfolioResponseDTO();
            dto.setStockSymbol(p.getStockSymbol());
            dto.setQuantity(p.getQuantity());
            dto.setAvgPrice(p.getAvgPrice());
            dto.setCurrentPrice(currentPrice);
            dto.setProfitLoss(profitLoss);

            responseList.add(dto);
        }

        return responseList;
    }
}