package org.example.app;

public class EndOfFileException extends Throwable {
    public EndOfFileException(String errorMessage) {
        super(errorMessage);
    }
}
