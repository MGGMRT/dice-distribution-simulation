package com.avalog.dicedistributionsimulation.exception;

public class DiceSimulationException extends RuntimeException {

  private ErrorType errorType;
  private String field;

  public DiceSimulationException(ErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public DiceSimulationException(ErrorType errorType, String field) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.field = field;
  }
}
