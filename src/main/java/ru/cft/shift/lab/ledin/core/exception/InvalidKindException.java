package ru.cft.shift.lab.ledin.core.exception;

public class InvalidKindException extends RuntimeException {
    public InvalidKindException() {
        super("api.intervals.api-responses.400.wrong-kind.description");
    }
}
