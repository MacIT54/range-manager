package ru.cft.shift.lab.ledin.rangemanager.core.utils.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shift.lab.ledin.rangemanager.api.dto.IntervalDto;
import ru.cft.shift.lab.ledin.rangemanager.core.exception.IntervalValueException;
import ru.cft.shift.lab.ledin.rangemanager.core.utils.validation.impl.IntervalValidateImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class IntervalValidateTest {

    private IntervalValidateImpl intervalValidate;

    @BeforeEach
    void setUp() {
        intervalValidate = new IntervalValidateImpl();
    }

    @Test
    void testValidateIntervalLengthWithValidIntervals() {
        List<IntervalDto<?>> validIntervals = Arrays.asList(
                new IntervalDto<>(1, 5),
                new IntervalDto<>(10, 20)
        );

        intervalValidate.validateIntervalLength(validIntervals);
    }

    @Test
    void testValidateIntervalLengthWithInvalidIntervals() {
        List<IntervalDto<?>> invalidIntervals = Arrays.asList(
                new IntervalDto<>(1, 2),
                new IntervalDto<>()
        );

        assertThrows(IntervalValueException.class, () -> {
            intervalValidate.validateIntervalLength(invalidIntervals);
        });
    }

    @Test
    void testValidateIntervalLetterWithValidIntervals() {
        List<IntervalDto<?>> validIntervals = Arrays.asList(
                new IntervalDto<>("A", "B"),
                new IntervalDto<>("C", "D")
        );

        intervalValidate.validateIntervalLetter(validIntervals);
    }

    @Test
    void testValidateIntervalLetterWithInvalidIntervals() {
        List<IntervalDto<?>> invalidIntervals = Arrays.asList(
                new IntervalDto<>("A", "1"),
                new IntervalDto<>("C", "DD")
        );

        assertThrows(IntervalValueException.class, () -> {
            intervalValidate.validateIntervalLetter(invalidIntervals);
        });
    }

    @Test
    void testValidateIntervalDigit_withValidIntervals() {
        List<IntervalDto<?>> validIntervals = Arrays.asList(
                new IntervalDto<>(1, 2),
                new IntervalDto<>(10, 20)
        );

        intervalValidate.validateIntervalDigit(validIntervals);
    }

    @Test
    void testValidateIntervalDigitWithInvalidIntervals() {
        List<IntervalDto<?>> invalidIntervals = Arrays.asList(
                new IntervalDto<>("A", "B"),
                new IntervalDto<>("C", "D")
        );

        assertThrows(IntervalValueException.class, () -> {
            intervalValidate.validateIntervalDigit(invalidIntervals);
        });
    }

    @Test
    void testValidateIntervalBorderWithValidBorders() {
        intervalValidate.validateIntervalBorder(1, 5);
        intervalValidate.validateIntervalBorder("A", "B");
    }

    @Test
    void testValidateIntervalBorderWithInvalidBorders() {
        assertThrows(IntervalValueException.class, () -> {
            intervalValidate.validateIntervalBorder(5, 1);
        });

        assertThrows(IntervalValueException.class, () -> {
            intervalValidate.validateIntervalBorder("B", "A");
        });
    }
}
