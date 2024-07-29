package ru.cft.shift.lab.ledin.rangemanager.core.exception;

public class MessageException extends RuntimeException {
    protected final int code;

    public MessageException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
