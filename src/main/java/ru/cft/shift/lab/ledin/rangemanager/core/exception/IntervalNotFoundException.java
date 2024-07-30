package ru.cft.shift.lab.ledin.rangemanager.core.exception;

public class IntervalNotFoundException extends RuntimeException {
    public IntervalNotFoundException() {
        super("api.intervals.get-min.api-responses.404.description");
    }
}
