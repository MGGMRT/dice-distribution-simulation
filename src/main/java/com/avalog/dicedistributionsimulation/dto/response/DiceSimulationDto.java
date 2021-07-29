package com.avalog.dicedistributionsimulation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class DiceSimulationDto {
  int numOfRolls;
  int numOfDice;
  int numOfDiceSide;
  List<DiceRollerDto> rollingDice;
}
