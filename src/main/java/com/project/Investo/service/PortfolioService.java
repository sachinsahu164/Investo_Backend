package com.project.Investo.service;

import com.project.Investo.dto.response.PortfolioResponseDTO;

import java.util.List;

public interface PortfolioService {

    List<PortfolioResponseDTO> getUserPortfolio();
}
