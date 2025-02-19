package ru.cft.shift.lab.ledin.rangemanager.core.utils.validation;

import ru.cft.shift.lab.ledin.rangemanager.api.dto.IntervalDto;

import java.util.List;

public interface IntervalValidate {
    void validateIntervalLength (List<IntervalDto<?>> intervalDto);
    void validateIntervalLetter(List<IntervalDto<?>> intervalDto);
    void validateIntervalDigit(List<IntervalDto<?>> intervalDto);
    <T extends Comparable<T>> void validateIntervalBorder(T start, T end);
}
