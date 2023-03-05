package es.asanchez.exception.handler;

import es.asanchez.dto.FailedValidationResponse;
import es.asanchez.exception.InputValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class InputValidationHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<FailedValidationResponse> handlerException(InputValidationException ex){
        FailedValidationResponse response = new FailedValidationResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setInput(ex.getINPUT());
        response.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public Mono<ResponseEntity<Object>> handlerNse(NoSuchElementException e,ServerWebExchange swe){
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,e.getMessage());
        return super.handleResponseStatusException(exception,null,exception.getStatusCode(),swe);
    }



}
