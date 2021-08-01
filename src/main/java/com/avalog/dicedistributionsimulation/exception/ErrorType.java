package com.avalog.dicedistributionsimulation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
  INTERNAL_ERROR("100000", "An internal server error.", INTERNAL_SERVER_ERROR),
  INVALID_PARAMETER_ERROR("100001", "Invalid field(s). ", BAD_REQUEST),
  DICE_ROLL_CALLCULATION(
      "100002",
      "Dice roll process problem. please contact consumer service. ",
      INTERNAL_SERVER_ERROR);

  private String code;
  private String message;
  private HttpStatus httpStatus;
}
