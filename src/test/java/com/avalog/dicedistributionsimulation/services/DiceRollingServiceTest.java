package com.avalog.dicedistributionsimulation.services;

import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceRolledDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import com.avalog.dicedistributionsimulation.model.Simulation;
import com.avalog.dicedistributionsimulation.repository.DiceDistributionRecordRepository;
import com.avalog.dicedistributionsimulation.repository.SimulationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.avalog.dicedistributionsimulation.utility.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiceRollingServiceTest {

    @Mock
    SimulationRepository simulationRepository;
    @Mock
    DiceRollCalculator diceRollCalculator;
    @Mock
    DiceDistributionRecordRepository diceDistributionRecordRepository;

    @InjectMocks
    DiceRollingService diceRollingService;

    @Test
    public void diceRolling_returnDiceSimulationDtoWhenCallWithArguments(){
        //GIVEN
        List<DiceRolledDto> diceRolledDtoList = getDiceRolledDtoList();
        Simulation simulation = getSimulation();
        DiceDistributionRecord diceDistributionRecord = getDiceDistributionRecord();
        when(diceRollCalculator.diceRollingCalculation(anyInt(),anyInt(),anyInt())).thenReturn(diceRolledDtoList);
        when(simulationRepository.save(any(Simulation.class))).thenReturn(simulation);
        when(diceDistributionRecordRepository.save(any(DiceDistributionRecord.class))).thenReturn(diceDistributionRecord);

        //WHEN
        DiceSimulationDto diceSimulationDto = diceRollingService.diceRolling(DEFAULT_DICE_ROLL_NUMBER, DEFAULT_DICE_NUMBER, DEFAULT_DICE_SIDES_NUMBER);

        //THEN
        assertNotNull(diceSimulationDto);
        verify(diceRollCalculator,times(1)).diceRollingCalculation(anyInt(),anyInt(),anyInt());
        verify(simulationRepository,times(1)).save(any(Simulation.class));
        verify(diceDistributionRecordRepository,times(1)).save(any(DiceDistributionRecord.class));
    }

    @Test
    public void getSimulationCombinationList_returnCombinationDtoListWhenCall() {
        //GIVEN
        List<CombinationDto> combinationDtoList =  buildCombinationDtoList();
        when(simulationRepository.getSimulationAggByDiceCountAndDiceSide()).thenReturn(combinationDtoList);

        //WHEN
        List<CombinationDto> simulationCombinationList = diceRollingService.getSimulationCombinationList();

        //THEN
        assertNotNull(simulationCombinationList);
        verify(simulationRepository,times(1)).getSimulationAggByDiceCountAndDiceSide();
    }

    @Test
    public void getSimulationProbabilityList_returnProbabilityDistributionDtoListWhenCall() {
        //GIVEN
        List<ProbabilityDistributionDto> probabilityDistributionDtoList = buildProbabilityDistributionDtoList();
        when(simulationRepository.getSimulationProbability()).thenReturn(probabilityDistributionDtoList);

        //WHEN
        List<ProbabilityDistributionDto> simulationProbabilityList = diceRollingService.getSimulationProbabilityList();

        //THEN
        assertNotNull(simulationProbabilityList);
        verify(simulationRepository,times(1)).getSimulationProbability();
    }
}