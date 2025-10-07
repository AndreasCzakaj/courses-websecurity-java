package com.websecurity.testing;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ValidationTest {
    Validation validation;

    @BeforeEach
    void beforeEach() {
        validation = new Validation();
    }

    @ParameterizedTest(name = "it should yield {1} for input {0}")
    @MethodSource("shouldPassParams")
    void shouldPass(String input, String expectedSanitizedInput) {
        var actual = validation.checkAndSanitize(input);
        assertThat(actual).isEqualTo(expectedSanitizedInput);
    }

    static Stream<Arguments> shouldPassParams() {
        return Stream.of(
                arguments("CJ",         "CJ"),
                arguments("Joey ",      "Joey")/*,
                arguments("<script",    "script")*/
        );
    }

    @ParameterizedTest(name = "it should fail for input {0} with message {1}")
    @MethodSource("shouldFailParams")
    void shouldFail(String input, String expected) {
        assertThatThrownBy(() -> validation.checkAndSanitize(input))
                //.isInstanceOf(IllegalArgumentException.class)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessage(expected);
    }

    static Stream<Arguments> shouldFailParams() {
        return Stream.of(
                /*arguments(null, "Input must not be null"),
                arguments("", "Input must be at least 1 char"),
                arguments(" ", "Input must be at least 1 char")*/
                arguments(null, "firstName: darf nicht null sein"),
                arguments("", "firstName: Größe muss zwischen 1 und 2147483647 sein"),
                arguments(" ", "firstName: Größe muss zwischen 1 und 2147483647 sein")
        );
    }
}
