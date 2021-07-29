package com.avalog.dicedistributionsimulation.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@Entity(name = "simulation")
public class Simulation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int simulationId;

  @Column(name = "number_of_rolls")
  private int numberOfRolls;

  @Column(name = "dice_side")
  private int diceSides;

  @Column(name = "dice_count")
  private int diceCount;

  @Column(name = "created_date")
  private LocalDateTime createdTime;

  @PrePersist
  private void prePersistFunction() {
    createdTime = LocalDateTime.now();
  }
}
