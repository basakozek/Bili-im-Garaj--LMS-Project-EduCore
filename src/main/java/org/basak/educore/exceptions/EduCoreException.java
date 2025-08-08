package org.basak.educore.exceptions;

public class EduCoreException extends RuntimeException {
    private ErrorType errorType;

    public EduCoreException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}