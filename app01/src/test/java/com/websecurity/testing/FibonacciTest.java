package com.websecurity.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static  org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class FibonacciTest {
    Fibonacci fibonacci;

    @BeforeEach
    void beforeEach() {
        fibonacci = new Fibonacci();
    }

    @ParameterizedTest(name = "it should yield {1} for index #{0}")
    @MethodSource("shouldPassParams")
    void shouldPass(Integer index, Integer expected) {
        assertThat(fibonacci.calculate(index)).isEqualTo(expected);
    }

    static Stream<Arguments> shouldPassParams() {
        return Stream.of(
                arguments(0, 0)/*,
                arguments(1, 1),
                arguments(2, 1),
                arguments(3, 2),
                arguments(4, 3),
                arguments(5, 8),
                arguments(6, 8),
                arguments(19, 4181)*/
        );
    }

    @ParameterizedTest(name = "it should fail for index #{0} with message {1}")
    @MethodSource("shouldFailParams")
    void shouldFail(Integer index, String expected) {
        assertThatThrownBy(() -> fibonacci.calculate(index))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expected);
    }

    static Stream<Arguments> shouldFailParams() {
        return Stream.of(
                arguments(null, "Index must not be null")/*,
                arguments(-1, "Index must be >= 0"),
                arguments(47, "Index must be <= 46")*/
        );
    }
}
