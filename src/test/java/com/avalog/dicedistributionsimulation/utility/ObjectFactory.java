package com.avalog.dicedistributionsimulation.utility;

import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceRolledDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import com.avalog.dicedistributionsimulation.model.Simulation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ObjectFactory {

  private static final String percentage = "12.79%";

  public static final int DEFAULT_DICE_ROLL_NUMBER = 100;
  public static final int DEFAULT_DICE_NUMBER = 3;
  public static final int DEFAULT_DICE_SIDES_NUMBER = 6;

  public static LocalDateTime localDateTime = LocalDateTime.now();

  public static List<DiceRolledDto> getDiceRolledDtoList() {

    return Arrays.asList(new DiceRolledDto(2l, 4));
  }

  public static List<CombinationDto> buildCombinationDtoList() {
    CombinationDto combinationDto =
        CombinationDto.builder()
            .numOfDiceSide(6)
            .numOfDice(3)
            .numOfSimulation(4)
            .sumOfRolledDice(34)
            .build();

    return Arrays.asList(combinationDto);
  }

  public static List<ProbabilityDistributionDto> buildProbabilityDistributionDtoList() {
    ProbabilityDistributionDto probabilityDistributionDto =
        ProbabilityDistributionDto.builder()
            .times(6)
            .sumOfRolledDice(23)
            .percent(percentage)
            .build();

    return Arrays.asList(probabilityDistributionDto);
  }

  public static Simulation getSimulation() {
    return Simulation.builder()
        .Id(0)
        .diceCount(3)
        .numberOfRolls(100)
        .diceSides(6)
        .createdTime(null)
        .build();
  }

  public static DiceDistributionRecord getDiceDistributionRecord() {
    return DiceDistributionRecord.builder()
        .sumRolledNumberDice(12)
        .rolledTimes(100l)
        .createdTime(localDateTime)
        .build();
  }

  public static DiceSimulationDto getDiceRollerDtoList() {
    List<DiceRolledDto> diceRolledDtoList = getDiceRolledDtoList();
    return DiceSimulationDto.builder()
        .numOfDiceSide(DEFAULT_DICE_SIDES_NUMBER)
        .numOfDice(DEFAULT_DICE_NUMBER)
        .numOfRolls(DEFAULT_DICE_ROLL_NUMBER)
        .rollingDice(diceRolledDtoList)
        .build();
  }
}
