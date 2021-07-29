package com.avalog.dicedistributionsimulation.services;

import com.avalog.dicedistributionsimulation.dto.ICombination;
import com.avalog.dicedistributionsimulation.dto.IProbabilityDistribution;
import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceRollerDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.exception.DiceSimulationException;
import com.avalog.dicedistributionsimulation.mapper.CombinationMapper;
import com.avalog.dicedistributionsimulation.mapper.ProbabilityDistributionMapper;
import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import com.avalog.dicedistributionsimulation.model.Simulation;
import com.avalog.dicedistributionsimulation.repository.DiceDistributionRecordRepository;
import com.avalog.dicedistributionsimulation.repository.SimulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.avalog.dicedistributionsimulation.exception.ErrorType.*;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
@Slf4j
public class DiceRollerService {

  private final SimulationRepository simulationRepository;

  private final DiceDistributionRecordRepository diceDistributionRecordRepository;

  private final CombinationMapper combinationMapper;

  private final ProbabilityDistributionMapper probabilityDistributionMapper;

  @Transactional()
  public DiceSimulationDto diceRollerList(int numberOfRolls, int numOfDice, int numOfSide) {
    log.info(
        "dice roller proccess is started. number of rolles {} , number of Dice {}, "
            + "number of Dice side {} .",
        numberOfRolls,
        numOfDice,
        numOfSide);

    List<Integer> diceRollList = new ArrayList<>();
    diceRollerCalculator(numberOfRolls, numOfDice, numOfSide).forEach(diceRollList::add);

    List<DiceRollerDto> diceRollerDtoList = getTimesOfRollerSum(diceRollList);

    Simulation simulation =
        Simulation.builder()
            .numberOfRolls(numberOfRolls)
            .diceSides(numOfSide)
            .diceCount(numOfDice)
            .build();

    saveDiceRollerRecords(simulation, diceRollerDtoList);

    return DiceSimulationDto.builder()
        .numOfDiceSide(numOfSide)
        .numOfDice(numOfDice)
        .numOfRolls(numberOfRolls)
        .rollingDice(diceRollerDtoList)
        .build();
  }

  private List<Integer> diceRollerCalculator(int numberOfRolls, int numOfDice, int numOfSide) {
    log.info(
        "dice roller calculator is running. number of rolles {} , number of Dice {}, "
            + "number of Dice side {} .",
        numberOfRolls,
        numOfDice,
        numOfSide);
    return IntStream.range(0, numberOfRolls)
        .map(i -> this.rolledDiceSideSum.apply(numOfDice, numOfSide))
        .boxed()
        .collect(Collectors.toList());
  }

  private Function<Integer, Integer> diceRandomNumberGenerator =
      (n) -> {
        try {
          Random random = SecureRandom.getInstanceStrong();
          return random.nextInt(n) + 1;
        } catch (NoSuchAlgorithmException e) {
          throw new DiceSimulationException(DICE_ROLLER_SUM_CALLCULATION);
        }
      };

  private BiFunction<Integer, Integer, Integer> rolledDiceSideSum =
      (numOfDice, numOfSide) ->
          IntStream.range(0, numOfDice).map(i -> diceRandomNumberGenerator.apply(numOfSide)).sum();

  private List<DiceRollerDto> getTimesOfRollerSum(List<Integer> diceRollSumList) {
    Map<Integer, Long> aggregatedDiceRollSumValue =
        diceRollSumList.stream().collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    List<DiceRollerDto> diceRollerSumList = diceRollerList(aggregatedDiceRollSumValue);
    return diceRollerSumList;
  }

  private List<DiceRollerDto> diceRollerList(Map<Integer, Long> aggregatedDiceRollSumValue) {
    Set<Integer> keySetValue = aggregatedDiceRollSumValue.keySet();
    List<DiceRollerDto> diceRollerDtoList = new ArrayList<>();
    keySetValue.forEach(
        key -> {
          diceRollerDtoList.add(new DiceRollerDto(aggregatedDiceRollSumValue.get(key), key));
        });
    return diceRollerDtoList;
  }

  private void saveDiceRollerRecords(Simulation simulation, List<DiceRollerDto> diceRollerDtoList) {
    Simulation savedSimulation = saveSimulation(simulation);
    if (isNull(savedSimulation)) {
      throw new DiceSimulationException(SIMULATION_NOT_SAVED);
    }
    saveDistributionRecord(diceRollerDtoList, savedSimulation);
  }

  private Simulation saveSimulation(Simulation simulation) {
    Simulation savedSimulation = simulationRepository.save(simulation);
    return savedSimulation;
  }

  private void saveDistributionRecord(
      List<DiceRollerDto> diceRollerDtoList, Simulation savedSimulation) {
    diceRollerDtoList.stream()
        .map(
            item ->
                DiceDistributionRecord.builder()
                    .simulationId(savedSimulation.getSimulationId())
                    .rolledTimes(item.getTimes().intValue())
                    .sumRolledNumberDice(item.getSumOfRolledDice())
                    .build())
        .forEach(diceDistributionRecordRepository::save);

    diceDistributionRecordRepository
        .findById(Long.valueOf(savedSimulation.getSimulationId()))
        .orElseThrow(() -> new DiceSimulationException(DICE_DISTRIBUTION_RECORD_NOT_SAVED));
  }

  public List<CombinationDto> getCombinationList() {
    Collection<ICombination> simulationByDiceCountAndDiceSide =
        simulationRepository.findSimulationByDiceCountAndDiceSide();
    return combinationMapper.toListSimulationStatisticsDtoList(
        (List<ICombination>) simulationByDiceCountAndDiceSide);
  }

  public List<ProbabilityDistributionDto> getProbabilityList() {
    Collection<IProbabilityDistribution> relativeDistribution =
        diceDistributionRecordRepository.findRelativeDistribution();
    return probabilityDistributionMapper.toProbabilityDistributionDtoList(
        (List<IProbabilityDistribution>) relativeDistribution);
  }
}
