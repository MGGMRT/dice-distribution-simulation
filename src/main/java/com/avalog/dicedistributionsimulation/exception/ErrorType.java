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
  EMPTY_DICE_SUM_LIST("100001", "Dice roll sum list is empty fie.", INTERNAL_SERVER_ERROR),
  SIMULATION_NOT_SAVED(
      "100002", "Simulation not saved, please contact consumer service. ", INTERNAL_SERVER_ERROR),
  INVALID_PARAMETER_ERROR("100003", "Invalid field(s). ", BAD_REQUEST),
  DICE_ROLLER_SUM_CALLCULATION(
      "100004",
      "Dice roll calculation problem. please contact consumer service. ",
      INTERNAL_SERVER_ERROR),
  DICE_DISTRIBUTION_RECORD_NOT_SAVED(
      "100005", "Dice distribution record not saved, please contact consumer service. ", INTERNAL_SERVER_ERROR);

  private String code;
  private String message;
  private HttpStatus httpStatus;
}
