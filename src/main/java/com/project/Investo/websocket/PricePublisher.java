package com.project.Investo.websocket;



import com.project.Investo.simulation.PriceSimulationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PricePublisher {

    private final SimpMessagingTemplate messagingTemplate;
    private final PriceSimulationService simulationService;

    public PricePublisher(SimpMessagingTemplate messagingTemplate,
                          PriceSimulationService simulationService) {
        this.messagingTemplate = messagingTemplate;
        this.simulationService = simulationService;
    }

    @Scheduled(fixedRate = 3000)
    public void publishPrices() {

        Map<String, Double> prices = simulationService.getAllPrices();

        messagingTemplate.convertAndSend("/topic/prices", prices);
    }
}