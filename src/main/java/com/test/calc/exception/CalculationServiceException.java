package com.test.calc.exception;

public class CalculationServiceException extends RuntimeException {
    public CalculationServiceException(String errorMessage) {
        super(errorMessage);
    }
}
