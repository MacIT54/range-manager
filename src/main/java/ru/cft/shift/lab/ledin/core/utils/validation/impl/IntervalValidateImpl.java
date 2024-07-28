package ru.cft.shift.lab.ledin.core.utils.validation.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.cft.shift.lab.ledin.api.dto.IntervalDto;
import ru.cft.shift.lab.ledin.core.exception.IntervalValueException;
import ru.cft.shift.lab.ledin.core.utils.validation.IntervalValidate;

import java.util.List;

@Slf4j
@Component
public class IntervalValidateImpl implements IntervalValidate {
    public void validateIntervalLength (List<IntervalDto<?>> intervalDto) {
        for (List<?> interval : intervalDto) {
            if (interval.size() != 2) {
                log.error("Invalid interval length");
                throw new IntervalValueException();
            }
        }
    }

    private void validateLetter(String letter) {
        if (!letter.matches("[A-Za-z]")) {
            log.error("Each value in the interval must be a letter of the English alphabet");
            throw new IntervalValueException();
        }
    }

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

    public void validateIntervalDigit(List<IntervalDto<?>> intervalDto) {
        boolean invalidInterval = intervalDto.stream().anyMatch(interval ->
                !(interval.getStart() instanceof Integer && interval.getEnd() instanceof Integer)
        );

        if (invalidInterval) {
            log.error("Each value in the interval must be a type Integer.");
            throw new IntervalValueException();
        }
    }

    public <T extends Comparable<T>> void validateIntervalBorder(T start, T end) {
        if (start.compareTo(end) >= 0) {
            log.error("Invalid interval borders");
            throw new IntervalValueException();
        }
    }
}
