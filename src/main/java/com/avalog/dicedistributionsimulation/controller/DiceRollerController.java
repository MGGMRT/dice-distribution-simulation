package com.avalog.dicedistributionsimulation.controller;

import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.services.DiceRollerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.avalog.dicedistributionsimulation.constants.DiceSimulationConstants.*;
import static com.avalog.dicedistributionsimulation.constants.RestApiUrlConstants.BASE;
import static com.avalog.dicedistributionsimulation.constants.RestApiUrlConstants.ROLLING_DICES;

@RestController
@RequestMapping(BASE)
@Slf4j
@Validated
@RequiredArgsConstructor
public class DiceRollerController {

    private final DiceRollerService diceRollerService;

    @PostMapping(path = ROLLING_DICES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiceSimulationDto> diceRoller(
            @RequestParam(name = "numberOfRolls", required = true, defaultValue = DEFAULT_DICE_ROLL_NUMBER)
            @Min(1) int numberOfRolls,
            @RequestParam(name = "numOfDice", required = true, defaultValue = DEFAULT_DICE_NUMBER)
            @Min(MIN_DICE_ROLL_NUMBER)
            @Max(MAX_DICE_NUMBER)
                    int numOfDice,
            @RequestParam(name = "numOfSide", required = true, defaultValue = DEFAULT_DICE_SIDES_NUMBER)
            @Min(MIN_DICE_SIDES_NUMBER)
            @Max(MAX_DICE_SIDES_NUMBER)
                    int numOfSide) {
        DiceSimulationDto diceRollerDtoList =
                diceRollerService.diceRollerList(numberOfRolls, numOfDice, numOfSide);
        return ResponseEntity.ok(diceRollerDtoList);
    }
}
