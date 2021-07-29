package com.avalog.dicedistributionsimulation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName();
    String type = ex.getRequiredType().getSimpleName();
    Object value = ex.getValue();
    String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);
    Error error = new Error(ErrorType.INVALID_PARAMETER_ERROR.getCode(), message, name);
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex) {
    List<Error> errorList =
        ex.getConstraintViolations().stream()
            .map(
                er ->
                    new Error(
                        ErrorType.INVALID_PARAMETER_ERROR.getCode(),
                        er.getMessage(),
                        er.getPropertyPath().toString()))
            .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(new ErrorList(errorList));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception ex) {
    log.error("Error occurred. {} ", ex.getMessage());
    Error error =
        new Error(ErrorType.INTERNAL_ERROR.getCode(), ErrorType.INTERNAL_ERROR.getMessage(), null);
    return ResponseEntity.internalServerError().body(error);
  }
}
