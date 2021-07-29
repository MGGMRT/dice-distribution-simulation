package com.avalog.dicedistributionsimulation.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(name = "CombinationDto")
public class CombinationDto {
  int numOfSimulation;
  int sumOfRolledDice;
  int numOfDice;
  int numOfDiceSide;
}
