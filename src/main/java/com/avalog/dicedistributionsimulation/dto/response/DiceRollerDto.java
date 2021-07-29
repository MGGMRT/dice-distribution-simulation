package com.avalog.dicedistributionsimulation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "DiceRollerDto")
public class DiceRollerDto {
  public Long times;
  public Integer sumOfRolledDice;
}
