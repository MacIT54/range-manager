package ru.cft.shift.lab.ledin.rangemanager.core.service;


import ru.cft.shift.lab.ledin.rangemanager.api.dto.IntervalDto;

import java.util.List;

public interface IntervalService {
    IntervalDto<?> getMinInterval(String kind);
    void mergeIntervals(String kind, List<IntervalDto<?>> intervalDTOS);
}
