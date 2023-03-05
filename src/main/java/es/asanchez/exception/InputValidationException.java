package es.asanchez.exception;

import org.springframework.http.HttpStatus;

public class InputValidationException extends Exception {

    private static final String MSG = "El rango permitido es entre 10 y 20";
    private final int errorCode = HttpStatus.BAD_REQUEST.value();
    private final int INPUT;

    public InputValidationException(int input) {
        super(MSG);
        INPUT = input;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getINPUT() {
        return INPUT;
    }
}
