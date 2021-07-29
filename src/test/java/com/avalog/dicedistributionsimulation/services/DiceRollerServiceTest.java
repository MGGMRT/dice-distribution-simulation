package com.avalog.dicedistributionsimulation.services;

import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.exception.DiceSimulationException;
import com.avalog.dicedistributionsimulation.mapper.CombinationMapper;
import com.avalog.dicedistributionsimulation.mapper.ProbabilityDistributionMapper;
import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import com.avalog.dicedistributionsimulation.model.Simulation;
import com.avalog.dicedistributionsimulation.repository.DiceDistributionRecordRepository;
import com.avalog.dicedistributionsimulation.repository.SimulationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.avalog.dicedistributionsimulation.utility.ObjectFactory.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiceRollerServiceTest {

  @Mock private SimulationRepository simulationRepository;

  @Mock private DiceDistributionRecordRepository diceDistributionRecordRepository;

  @Mock private CombinationMapper combinationMapper;

  @Mock private ProbabilityDistributionMapper probabilityDistributionMapper;

  @InjectMocks private DiceRollerService diceRollerService;

  @Test
  public void
      diceRollerList_returnDiceSimulationExceptionWhenDiceDistributionRecordSaveNotWorkProperly() {
    // GIVEN
    Simulation simulation = getSimulation();
    when(simulationRepository.save(any(Simulation.class))).thenReturn(simulation);
    when(diceDistributionRecordRepository.save(any(DiceDistributionRecord.class))).thenReturn(null);
    when(diceDistributionRecordRepository.findById(anyLong())).thenReturn(empty());

    // WHEN
    Assertions.assertThrows(
        DiceSimulationException.class,
        () -> {
          DiceSimulationDto diceSimulationDto =
              diceRollerService.diceRollerList(
                  DEFAULT_DICE_ROLL_NUMBER, DEFAULT_DICE_NUMBER, DEFAULT_DICE_SIDES_NUMBER);
        });
  }

  @Test
  public void diceRollerList_returnDiceSimulationExceptionWhenSimulationSaveNotWorkProperly() {
    // GIVEN
    when(simulationRepository.save(any(Simulation.class))).thenReturn(null);

    // WHEN
    Assertions.assertThrows(
        DiceSimulationException.class,
        () -> {
          DiceSimulationDto diceSimulationDto =
              diceRollerService.diceRollerList(
                  DEFAULT_DICE_ROLL_NUMBER, DEFAULT_DICE_NUMBER, DEFAULT_DICE_SIDES_NUMBER);
        });
  }

  @Test
  public void diceRollerList_returnDiceSimulationDtoWhenValidArgumantsPassed() {
    // GIVEN
    Simulation simulation = getSimulation();
    DiceDistributionRecord diceDistributionRecord = getDiceDistributionRecord();
    when(diceDistributionRecordRepository.save(any(DiceDistributionRecord.class)))
        .thenReturn(diceDistributionRecord);
    when(simulationRepository.save(any(Simulation.class))).thenReturn(simulation);
    when(diceDistributionRecordRepository.findById(anyLong()))
        .thenReturn(of(diceDistributionRecord));

    // WHEN
    DiceSimulationDto diceSimulationDto =
        diceRollerService.diceRollerList(
            DEFAULT_DICE_ROLL_NUMBER, DEFAULT_DICE_NUMBER, DEFAULT_DICE_SIDES_NUMBER);

    // THEN
    assertNotNull(diceSimulationDto);
    verify(simulationRepository, times(1)).save(simulation);
    verify(diceDistributionRecordRepository, atLeastOnce()).save(any(DiceDistributionRecord.class));
  }

  @Test
  public void getCombinationList_returnCombinationDtoListWhenCall() {
    // GIVEN
    List<CombinationDto> combinationDtos = buildCombinationDtoList();
    when(simulationRepository.findSimulationByDiceCountAndDiceSide()).thenReturn(iCombinationList);
    when(combinationMapper.toListSimulationStatisticsDtoList(anyList()))
        .thenReturn(combinationDtos);

    // WHEN
    List<CombinationDto> combinationList = diceRollerService.getCombinationList();

    // THEN
    assertNotNull(combinationList);
    verify(simulationRepository).findSimulationByDiceCountAndDiceSide();
    verify(combinationMapper).toListSimulationStatisticsDtoList(anyList());
  }

  @Test
  public void getProbabilityList_returnProbabilityDistributionDtoListWhenCall() {
    // GIVEN
    List<ProbabilityDistributionDto> probabilityDistributionDtoList =
        buildProbabilityDistributionDtoList();
    when(diceDistributionRecordRepository.findRelativeDistribution()).thenReturn(iProbabilityList);
    when(probabilityDistributionMapper.toProbabilityDistributionDtoList(anyList()))
        .thenReturn(probabilityDistributionDtoList);

    // WHEN
    List<ProbabilityDistributionDto> probabilityList = diceRollerService.getProbabilityList();

    // THEN
    assertNotNull(probabilityList);
    verify(diceDistributionRecordRepository, times(1)).findRelativeDistribution();
    verify(probabilityDistributionMapper).toProbabilityDistributionDtoList(anyList());
  }
}
