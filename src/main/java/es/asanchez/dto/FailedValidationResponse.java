package es.asanchez.dto;

import lombok.Data;

@Data
public class FailedValidationResponse {

    private int errorCode;
    private int input;
    private String message;


}
