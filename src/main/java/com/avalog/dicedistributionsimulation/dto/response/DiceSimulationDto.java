package com.avalog.dicedistributionsimulation.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@Schema(name = "DiceSimulationDto")
public class DiceSimulationDto {
  int numOfRolls;
  int numOfDice;
  int numOfDiceSide;
  List<DiceRolledDto> rollingDice;
}
