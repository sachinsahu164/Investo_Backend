package com.project.Investo.simulation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimulationScheduler {

    private final PriceSimulationService simulationService;

    public SimulationScheduler(PriceSimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @Scheduled(fixedRate = 3000)
    public void runSimulation() {

        simulationService.simulateAllPrices();

        System.out.println("Simulation running...");
    }
    @Scheduled(fixedRate = 3000)
    public void updateAll() {
        simulationService.simulateAllPrices();
    }
}
