package com.test.calc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class CalculationServiceTest {

    private final CalculationService calculationService = new CalculationService();
    private final double delta = 0.0000001;

    static Stream<Arguments> expressionsWithExpectedResults() {
        return Stream.of(
                arguments(9.2, "12.4-3.2"),
                arguments(-15.0, "-7.5*2"),
                arguments(-4.3, "-8.6/2"),
                arguments(1.0, "sin(pi/2)"),
                arguments(-1.0, "cos(pi)"),
                arguments(1.0, "tan(pi/4)"),
                arguments(1.0, "ctan(pi/4)"),
                arguments(8.0, "2^3"),
                arguments(9.0, "sqrt(81)"),
                arguments(-182.55868968175423, "sin(sqrt(2^5))+34+3*(-45/9823+36)*-2"),
                arguments(Double.NaN, "1/0"),
                arguments(5.0, "   3      +      2   ")
        );
    }

    @ParameterizedTest
    @MethodSource("expressionsWithExpectedResults")
    public void shouldPerformCalculationsForCorrectExpressions(Double expected, String expr) {
        assertEquals(expected, calculationService.calculate(expr), delta);
    }

    @Test
    void shouldVerifyAnExceptionOnIncorrectExpression() {
        var exception = assertThrows(CalculationServiceException.class, () ->
                calculationService.calculate("some incorrect expression"));
        assertEquals("Expression error", exception.getMessage());
    }
}