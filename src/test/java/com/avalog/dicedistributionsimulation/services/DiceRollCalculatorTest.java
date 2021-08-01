package com.avalog.dicedistributionsimulation.services;

import com.avalog.dicedistributionsimulation.dto.response.DiceRolledDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.avalog.dicedistributionsimulation.utility.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiceRollCalculatorTest {

    @InjectMocks
    DiceRollCalculator diceRollCalculator;

    @Test
    public void diceRollingCalculation_returnDiceRolledDtoListWhenCallWithArg(){
        //WHEN
        List<DiceRolledDto> diceRolledDtoList = diceRollCalculator.diceRollingCalculation(DEFAULT_DICE_ROLL_NUMBER, DEFAULT_DICE_NUMBER, DEFAULT_DICE_SIDES_NUMBER);

        //THEN
        assertNotNull(diceRolledDtoList);
    }


}