package ru.cft.shift.lab.ledin.core.exception;

public class CodeAbleException extends RuntimeException {
    protected final int code;

    public CodeAbleException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
