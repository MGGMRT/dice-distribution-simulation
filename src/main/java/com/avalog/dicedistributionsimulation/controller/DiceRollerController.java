package com.avalog.dicedistributionsimulation.controller;

import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.services.DiceRollerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.List;

import static com.avalog.dicedistributionsimulation.constants.DiceSimulationConstants.*;
import static com.avalog.dicedistributionsimulation.constants.RestApiUrlConstants.*;

@RestController
@RequestMapping(BASE)
@Slf4j
@Validated
@RequiredArgsConstructor
@Tag(name = "Dice Rolling Simulation")
public class DiceRollerController {

    private final DiceRollerService diceRollerService;
    @Operation(
            summary =
                    "Return list of dice rolling value simulation with given numbers of dice, dice - sides and  dice rolls times.",
            method = "POST")
    @ApiResponse(
            responseCode = "200",
            description =
                    "Returns list of  random rolling dice sum values and total number of repeated dice value sum.",
            content = @Content(schema = @Schema(implementation = DiceSimulationDto.class)))
    @ApiResponse(
            responseCode = "400",
            description = "Returned when request param is not in the valid range.")
    @PostMapping(path = ROLLING_DICES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiceSimulationDto> diceRoller(
            @RequestParam(name = "numberOfRolls", required = true, defaultValue = DEFAULT_DICE_ROLL_NUMBER)
            @Min(MIN_DICE_ROLL_NUMBER)
            @Max(MAX_DICE_ROLL_NUMBER) int numberOfRolls,
            @RequestParam(name = "numOfDice", required = true, defaultValue = DEFAULT_DICE_NUMBER)
            @Min(MIN_DICE_NUMBER)
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

    @Operation(
            summary =
                    "Return combination simulation result data which is grouped by dice count and dice side.",
            method = "GET")
    @ApiResponse(
            responseCode = "200",
            description =
                    "Returns simulation count and sum of dice side numbers by dice count and dice sides.",
            content = @Content(schema = @Schema(implementation = CombinationDto.class)))
    @GetMapping(path = LIST_OF_COMBINATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CombinationDto>> getlistOfCombinations() {
        List<CombinationDto> combinationDtoList = diceRollerService.getCombinationList();
        return ResponseEntity.ok(combinationDtoList);
    }

    @Operation(
            summary = "Return probability simulation result data sum, times and percent.",
            method = "GET")
    @ApiResponse(
            responseCode = "200",
            description =
                    "Returns simulation count and sum of dice side numbers by dice count and dice sides.",
            content = @Content(schema = @Schema(implementation = ProbabilityDistributionDto.class)))
    @GetMapping(path = PROBABILITY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProbabilityDistributionDto>> getListOfProbabilty() {
        List<ProbabilityDistributionDto> probabilityDistributionDtoList =
                diceRollerService.getProbabilityList();
        return ResponseEntity.ok(probabilityDistributionDtoList);
    }
}
