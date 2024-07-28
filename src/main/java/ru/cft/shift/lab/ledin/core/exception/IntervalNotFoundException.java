package ru.cft.shift.lab.ledin.core.exception;

public class IntervalNotFoundException extends RuntimeException {
    public IntervalNotFoundException() {
        super("api.intervals.get-min.api-responses.404.description");
    }
}
