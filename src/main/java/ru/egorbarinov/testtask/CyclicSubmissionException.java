package ru.egorbarinov.testtask;

public class CyclicSubmissionException extends Exception{
    public CyclicSubmissionException(String message) {
        super(message);
    }
}
