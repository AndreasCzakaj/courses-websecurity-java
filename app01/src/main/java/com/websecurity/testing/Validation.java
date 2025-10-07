package com.websecurity.testing;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.val;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class Validation {
    String checkAndSanitize(String input) {
        Person person = new Person();
        person.firstName = input == null ? null : input.trim();

        // Create and initialize the validator
        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
        validatorFactory.afterPropertiesSet();  // Required to initialize the validator
        Validator validator = validatorFactory;

        var problems = validator.validate(person);
        if (!problems.isEmpty()) {
            throw new ConstraintViolationException(problems);
        }


        /*if (input == null) {
            throw new IllegalArgumentException("Input must not be null");
        }
        String out = input.trim();*/

        /*if (out.length() < 1) {
            throw new IllegalArgumentException("Input must be at least 1 char");
        }*/

        return person.firstName;
    }

    static class Person {
        @jakarta.validation.constraints.NotNull
        @jakarta.validation.constraints.Size(min = 1)
        String firstName;
    }
}
