package ru.cft.shift.lab.ledin.core.entity;

import java.util.UUID;

public interface Interval <T extends Comparable<T>> extends Comparable<Interval<T>> {
    UUID getIntervalId();
    void setIntervalEnd(T intervalEnd);
    void setIntervalStart(T intervalStart);
    T getIntervalStart();
    T getIntervalEnd();
}
