package com.avalog.dicedistributionsimulation.constants;

public class DiceSimulationConstants {

  private DiceSimulationConstants() {}

  public static final int MIN_DICE_ROLL_NUMBER = 1;
  public static final int MIN_DICE_NUMBER = 1;
  public static final int MIN_DICE_SIDES_NUMBER = 4;

  public static final int MAX_DICE_ROLL_NUMBER = Integer.MAX_VALUE - 1;
  public static final int MAX_DICE_NUMBER = Integer.MAX_VALUE - 1;
  public static final int MAX_DICE_SIDES_NUMBER = Integer.MAX_VALUE - 1;

  public static final String DEFAULT_DICE_ROLL_NUMBER = "100";
  public static final String DEFAULT_DICE_NUMBER = "3";
  public static final String DEFAULT_DICE_SIDES_NUMBER = "6";
}
