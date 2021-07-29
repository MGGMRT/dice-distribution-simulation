package com.avalog.dicedistributionsimulation.utility;


import com.avalog.dicedistributionsimulation.dto.ICombination;
import com.avalog.dicedistributionsimulation.dto.IProbabilityDistribution;
import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import com.avalog.dicedistributionsimulation.model.Simulation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ObjectFactory {

  private static final String percentage = "12.79%";

  public static final int DEFAULT_DICE_ROLL_NUMBER = 100;
  public static final int DEFAULT_DICE_NUMBER = 3;
  public static final int DEFAULT_DICE_SIDES_NUMBER = 6;

  public static LocalDateTime localDateTime = LocalDateTime.now();


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

  private static final ICombination iCombination =
      new ICombination() {
        @Override
        public int getNumOfDice() {
          return 3;
        }

        @Override
        public int getNumOfDiceSide() {
          return 6;
        }

        @Override
        public int getSumOfRolledDice() {
          return 12;
        }

        @Override
        public int getNumOfSimulation() {
          return 1;
        }
      };

  public static final Collection<ICombination> iCombinationList =
      (Collection<ICombination>) Arrays.asList(iCombination);

  private static final IProbabilityDistribution iProbabilityDistribution =
      new IProbabilityDistribution() {
        @Override
        public int getSumOfRolledDice() {
          return 34;
        }

        @Override
        public int getTimes() {
          return 8;
        }

        @Override
        public String getPercent() {
          return percentage;
        }
      };

  public static final Collection<IProbabilityDistribution> iProbabilityList =
      (Collection<IProbabilityDistribution>) Arrays.asList(iProbabilityDistribution);

  public static Simulation getSimulation() {
      return Simulation.builder()
              .simulationId(0)
              .diceCount(3)
              .numberOfRolls(100)
              .diceSides(6)
              .createdTime(null)
              .build();
  }

  public static DiceDistributionRecord getDiceDistributionRecord() {
      return DiceDistributionRecord.builder()
              .sumRolledNumberDice(12)
              .rolledTimes(100)
              .simulationId(1)
              .createdTime(localDateTime)
              .build();
  }
}
