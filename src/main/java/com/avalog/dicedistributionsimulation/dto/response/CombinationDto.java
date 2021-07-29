package com.avalog.dicedistributionsimulation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CombinationDto {
  int numOfSimulation;
  int sumOfRolledDice;
  int numOfDice;
  int numOfDiceSide;
}
