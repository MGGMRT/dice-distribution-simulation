package com.avalog.dicedistributionsimulation.services;

import com.avalog.dicedistributionsimulation.dto.response.DiceRolledDto;
import com.avalog.dicedistributionsimulation.exception.DiceSimulationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.avalog.dicedistributionsimulation.exception.ErrorType.DICE_ROLL_CALLCULATION;

@Slf4j
@Component
public class DiceRollCalculator {

  public List<DiceRolledDto> diceRollingCalculation(
      int numberOfRolls, int numOfDice, int numOfSide) {
    List<Integer> diceRollList = new ArrayList<>();
    diceRollingCalculator(numberOfRolls, numOfDice, numOfSide).forEach(diceRollList::add);
    List<DiceRolledDto> diceRolledDtoList = groupingRolledDice(diceRollList);
    return diceRolledDtoList;
  }

  private List<Integer> diceRollingCalculator(int numberOfRolls, int numOfDice, int numOfSide) {
    log.info(
        "dice roller calculator is running. number of rolles {} , number of Dice {}, "
            + "number of Dice side {} .",
        numberOfRolls,
        numOfDice,
        numOfSide);
    return IntStream.range(0, numberOfRolls)
        .map(i -> this.rolledDiceSideSumFunc.apply(numOfDice, numOfSide))
        .boxed()
        .collect(Collectors.toList());
  }

  private final Function<Integer, Integer> randomDiceNumberGeneratorFunc =
      (numOfSide) -> {
        try {
          Random random = SecureRandom.getInstanceStrong();
          return random.nextInt(numOfSide) + 1;
        } catch (NoSuchAlgorithmException e) {
          log.error("problem occurred in the random number generator",e);
          throw new DiceSimulationException(DICE_ROLL_CALLCULATION);
        }
      };

  private final BiFunction<Integer, Integer, Integer> rolledDiceSideSumFunc =
      (numOfDice, numOfSide) ->
          IntStream.range(0, numOfDice).map(i -> randomDiceNumberGeneratorFunc.apply(numOfSide)).sum();

  private List<DiceRolledDto> groupingRolledDice(List<Integer> diceRollSumList) {
    Map<Integer, Long> aggregatedDiceRollSumValue =
        diceRollSumList.stream().collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    List<DiceRolledDto> diceRollerSumList = toDiceRolledDtoList(aggregatedDiceRollSumValue);
    return diceRollerSumList;
  }

  private List<DiceRolledDto> toDiceRolledDtoList(Map<Integer, Long> aggregatedDiceRollSumValue) {
    Set<Integer> keySetValue = aggregatedDiceRollSumValue.keySet();
    List<DiceRolledDto> diceRolledDtoList = new ArrayList<>();
    keySetValue.forEach(
        key -> {
          diceRolledDtoList.add(new DiceRolledDto(aggregatedDiceRollSumValue.get(key), key));
        });
    return diceRolledDtoList;
  }
}
