package com.fetch.receipt.processor.exception;

import com.fetch.receipt.processor.data.response.BadRequestResponse;
import com.fetch.receipt.processor.data.response.NotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BadRequestResponse> handleInvalidJson(HttpMessageNotReadableException ex) {
        BadRequestResponse error = new BadRequestResponse();
        error.setMessage("The receipt is invalid.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BadRequestResponse error = new BadRequestResponse();
        error.setMessage("The receipt is invalid.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(DateTimeParseException.class)
//    public ResponseEntity<BadRequestResponse> handleDateTimeValidationException(MethodArgumentNotValidException ex) {
//        BadRequestResponse error = new BadRequestResponse();
//        error.setMessage("The receipt is invalid. Please correct date/time input format fields");
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(UUIDNotFoundException.class)
    public ResponseEntity<NotFoundResponse> handleEmployeeNotFound(UUIDNotFoundException ex) {
        NotFoundResponse error = new NotFoundResponse();
        error.setMessage("No receipt found for that ID.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<NotFoundResponse> handleInvalidUUID(IllegalArgumentException ex) {
        NotFoundResponse error = new NotFoundResponse();
        error.setMessage("No receipt found for that ID.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
