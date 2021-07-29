package com.avalog.dicedistributionsimulation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiceRollerDto {
  public Long times;
  public Integer sumOfRolledDice;
}
