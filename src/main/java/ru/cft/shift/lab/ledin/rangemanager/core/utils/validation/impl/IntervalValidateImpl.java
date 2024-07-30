package ru.cft.shift.lab.ledin.rangemanager.core.utils.validation.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.cft.shift.lab.ledin.rangemanager.api.dto.IntervalDto;
import ru.cft.shift.lab.ledin.rangemanager.core.exception.IntervalValueException;
import ru.cft.shift.lab.ledin.rangemanager.core.utils.validation.IntervalValidate;

import java.util.List;

/**
 * This class provides methods for validating the length, type, and boundaries of intervals.
 */
@Slf4j
@Component
public class IntervalValidateImpl implements IntervalValidate {

    /**
     * Validates that each interval in the provided list contains exactly two elements.
     *
     * @param intervalDto the list of intervals to validate
     * @throws IntervalValueException if any interval does not contain exactly two elements
     */
    public void validateIntervalLength (List<IntervalDto<?>> intervalDto) {
        if (intervalDto == null || intervalDto.isEmpty()) {
            log.error("Interval is empty");
            throw new IntervalValueException();
        }
        for (List<?> interval : intervalDto) {
            if (interval.size() != 2) {
                log.error("Invalid interval length");
                throw new IntervalValueException();
            }
        }
    }

    /**
     * Validates that the provided string is a single letter of the English alphabet.
     *
     * @param letter the string to validate
     * @throws IntervalValueException if the string is not a single letter of the English alphabet
     */
    private void validateLetter(String letter) {
        if (!letter.matches("[A-Za-z]")) {
            log.error("Each value in the interval must be a letter of the English alphabet");
            throw new IntervalValueException();
        }
    }

    /**
     * Validates that each interval in the provided list consists of single-character strings
     * representing letters of the English alphabet.
     *
     * @param intervalDto the list of intervals to validate
     * @throws IntervalValueException if any interval does not consist of single-character strings
     */
    public void validateIntervalLetter(List<IntervalDto<?>> intervalDto) {
        boolean invalidInterval = intervalDto.stream().anyMatch(interval ->
                !(interval.getStart() instanceof String && interval.getEnd() instanceof String) ||
                        ((String) interval.getStart()).length() != 1 ||
                        ((String) interval.getEnd()).length() != 1
        );

        if (invalidInterval) {
            log.error("Each value in the interval must be a single character and of type String.");
            throw new IntervalValueException();
        }

        for (IntervalDto interval : intervalDto) {
            validateLetter((String) interval.getStart());
            validateLetter((String) interval.getEnd());
        }
    }

    /**
     * Validates that each interval in the provided list consists of integers.
     *
     * @param intervalDto the list of intervals to validate
     * @throws IntervalValueException if any interval does not consist of integers
     */
    public void validateIntervalDigit(List<IntervalDto<?>> intervalDto) {
        boolean invalidInterval = intervalDto.stream().anyMatch(interval ->
                !(interval.getStart() instanceof Integer && interval.getEnd() instanceof Integer)
        );

        if (invalidInterval) {
            log.error("Each value in the interval must be a type Integer.");
            throw new IntervalValueException();
        }
    }

    /**
     * Validates that the start value of the interval is strictly less than the end value.
     *
     * @param <T> the type of the interval values, which must be comparable
     * @param start the start value of the interval
     * @param end the end value of the interval
     * @throws IntervalValueException if the start value is not less than the end value
     */
    public <T extends Comparable<T>> void validateIntervalBorder(T start, T end) {
        if (start.compareTo(end) > 0) {
            log.error("Invalid interval borders");
            throw new IntervalValueException();
        }
    }
}
