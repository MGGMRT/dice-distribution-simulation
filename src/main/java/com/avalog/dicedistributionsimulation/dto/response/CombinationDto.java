package com.avalog.dicedistributionsimulation.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(name = "CombinationDto")
public class CombinationDto {
  long numOfSimulation;
  long sumOfRolledDice;
  int numOfDice;
  int numOfDiceSide;
}
