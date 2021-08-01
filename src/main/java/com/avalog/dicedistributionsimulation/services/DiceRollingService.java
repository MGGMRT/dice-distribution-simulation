package com.avalog.dicedistributionsimulation.services;

import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceRolledDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import com.avalog.dicedistributionsimulation.model.Simulation;
import com.avalog.dicedistributionsimulation.repository.DiceDistributionRecordRepository;
import com.avalog.dicedistributionsimulation.repository.SimulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DiceRollingService {

  private final SimulationRepository simulationRepository;
  private final DiceRollCalculator diceRollCalculator;
  private final DiceDistributionRecordRepository diceDistributionRecordRepository;

  @Transactional()
  public DiceSimulationDto diceRolling(int numberOfDiceRolls, int numOfDice, int numOfSide) {
    log.info(
        "dice roller proccess is started. number of rolles {} , number of Dice {}, "
            + "number of Dice side {} .",
        numberOfDiceRolls,
        numOfDice,
        numOfSide);

    List<DiceRolledDto> diceRolledDtoList =
        diceRollCalculator.diceRollingCalculation(numberOfDiceRolls, numOfDice, numOfSide);

    Simulation simulation =
        Simulation.builder()
            .numberOfRolls(numberOfDiceRolls)
            .diceSides(numOfSide)
            .diceCount(numOfDice)
            .build();

    saveSimulation(simulation, diceRolledDtoList);

    return DiceSimulationDto.builder()
        .numOfDiceSide(numOfSide)
        .numOfDice(numOfDice)
        .numOfRolls(numberOfDiceRolls)
        .rollingDice(diceRolledDtoList)
        .build();
  }

  private void saveSimulation(Simulation simulation, List<DiceRolledDto> diceRolledDtoList) {
    List<DiceDistributionRecord> diceDistributionRecordList =
        toDiceDistributionRecordList(diceRolledDtoList);
    simulationRepository.save(simulation);
    diceDistributionRecordList.forEach(
        item -> {
          item.setSimulation(simulation);
          diceDistributionRecordRepository.save(item);
        });
  }

  private List<DiceDistributionRecord> toDiceDistributionRecordList(
      List<DiceRolledDto> diceRolledDtoList) {
    return diceRolledDtoList.stream()
        .map(
            item -> {
              return DiceDistributionRecord.builder()
                  .rolledTimes(item.times)
                  .sumRolledNumberDice(item.sumOfRolledDice)
                  .build();
            })
        .collect(Collectors.toList());
  }

  public List<CombinationDto> getSimulationCombinationList() {
    return simulationRepository.getSimulationAggByDiceCountAndDiceSide();
  }

  public List<ProbabilityDistributionDto> getSimulationProbabilityList() {
    return simulationRepository.getSimulationProbability();
  }
}
