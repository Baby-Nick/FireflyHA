package org.example.app;

public class WrongURLFormatException extends Throwable {
    public WrongURLFormatException(String errorMessage) {
        super(errorMessage);
    }
}
