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

import static com.avalog.dicedistributionsimulation.exception.ErrorType.INVALID_PARAMETER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName();
    String type = ex.getRequiredType().getSimpleName();
    Object value = ex.getValue();
    String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);
    ErrorInfo errorInfo = new ErrorInfo(INVALID_PARAMETER_ERROR.getCode(),message, name);
    return ResponseEntity.badRequest().body(errorInfo);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex) {
    List<ErrorInfo> errorList =
        ex.getConstraintViolations().stream()
            .map(
                er ->
                    new ErrorInfo(
                        INVALID_PARAMETER_ERROR.getCode(),
                        er.getMessage(),
                        er.getPropertyPath().toString()))
            .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(new ErrorInfoList(errorList));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception ex) {
    log.error("Error occurred. {} ", ex.getMessage());
    ErrorInfo error =
        new ErrorInfo(ErrorType.INTERNAL_ERROR.getCode(), ErrorType.INTERNAL_ERROR.getMessage(), null);
    return ResponseEntity.internalServerError().body(error);
  }
}
