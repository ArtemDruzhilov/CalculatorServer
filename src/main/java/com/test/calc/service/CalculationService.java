package com.test.calc.service;

import com.test.calc.exception.CalculationServiceException;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;

/**
 * A service that performs mathematical calculations
 * Used library mXparser https://github.com/mariuszgromada/MathParser.org-mXparser
 * mXparser homepage http://mathparser.org
 */
@Service
public class CalculationService {

    /**
     * Calculates the result of an arithmetic expression
     * Example of valid expression: "2 + 2 * sin(pi/2)"
     * Example of invalid expression: "x + 1" (x is not a number or a function)
     * @param textualExpression input expression
     * @return result of calculation
     * @throws CalculationServiceException if the expression is invalid
     */
    public Double calculate(final String textualExpression) {
        var expression = new Expression(textualExpression);
        if (expression.checkSyntax()) {
            return expression.calculate();
        } else {
            throw new CalculationServiceException("Expression error");
        }
    }
}
