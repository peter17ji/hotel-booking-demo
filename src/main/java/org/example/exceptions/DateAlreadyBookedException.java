package org.example.exceptions;

public class DateAlreadyBookedException extends RuntimeException {
    public DateAlreadyBookedException(String msg) {
        super(msg);
    }
}
