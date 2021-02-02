package com.test.calc.service;

public class CalculationServiceException extends RuntimeException {
    public CalculationServiceException(String errorMessage) {
        super(errorMessage);
    }
}
