package com.luox6.battleship.exception;

public class DirectionNotSupportedException extends RuntimeException {
    public DirectionNotSupportedException(String formatted) {
        super(formatted);
    }
}
