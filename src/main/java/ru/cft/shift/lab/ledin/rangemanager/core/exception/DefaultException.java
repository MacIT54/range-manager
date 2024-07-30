package ru.cft.shift.lab.ledin.rangemanager.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DefaultException extends RuntimeException{
    public DefaultException(String message) {
        super(message);
    }
}