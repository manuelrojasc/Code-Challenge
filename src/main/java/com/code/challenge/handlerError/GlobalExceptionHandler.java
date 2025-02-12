package com.code.challenge.handlerError;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationErrors(WebExchangeBindException ex) {
        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse error = ErrorResponse.builder()
                .code("VAL-001")
                .message(message)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return Mono.just(ResponseEntity.status(error.getStatus()).body(error));
    }
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .code("ALM-001") // Usa el código de error correspondiente
                .message(ex.getReason()) // Obtén el mensaje de la excepción
                .status(ex.getStatus()) // Obtén el estado HTTP de la excepción
                .build();

        return Mono.just(ResponseEntity.status(error.getStatus()).body(error));
    }
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleAllExceptions(Exception ex) {
        log.error("Error no controlado: ", ex);

        ErrorResponse error = ErrorResponse.builder()
                .code("ERR-001")
                .message("Error interno del servidor")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return Mono.just(ResponseEntity.status(error.getStatus()).body(error));
    }
}
