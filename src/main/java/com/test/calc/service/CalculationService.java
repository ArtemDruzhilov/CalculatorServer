package com.test.calc.service;

import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    public Double calculate(final String textualExpression) {
        var expression = new Expression(textualExpression);
        if (expression.checkSyntax()) {
            return expression.calculate();
        } else {
            throw new CalculationServiceException("Expression error");
        }
    }
}
