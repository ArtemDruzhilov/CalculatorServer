package com.test.calc.endpoint;

import com.test.calc.service.CalculationService;
import com.test.calc.exception.CalculationServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class TcpServerEndpoint {

    private final Logger logger = LoggerFactory.getLogger(TcpServerEndpoint.class);

    @Autowired
    private final CalculationService calculationService;

    public TcpServerEndpoint(final CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @ServiceActivator(inputChannel = "inboundChannel")
    public String process(final String inputText) {
        String outputText;
        try {
            final Double result = calculationService.calculate(inputText);
            outputText = String.valueOf(result);
        } catch (CalculationServiceException e) {
            outputText = e.getMessage();
        }
        logger.info("Input: " + inputText + " Output: " + outputText);
        return outputText;
    }
}
