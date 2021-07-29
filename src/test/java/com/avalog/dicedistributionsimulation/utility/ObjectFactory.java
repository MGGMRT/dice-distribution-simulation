package com.avalog.dicedistributionsimulation.utility;


import com.avalog.dicedistributionsimulation.dto.ICombination;
import com.avalog.dicedistributionsimulation.dto.IProbabilityDistribution;
import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ObjectFactory {

  private static final String percentage = "12.79%";

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
}
