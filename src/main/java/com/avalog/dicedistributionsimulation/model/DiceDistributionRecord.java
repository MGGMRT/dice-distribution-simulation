package com.avalog.dicedistributionsimulation.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@Entity(name = "dice_distribution_record")
public class DiceDistributionRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "record_id")
  int recordId;

  @Column(name = "simulation_id")
  private int simulationId;

  @Column(name = "rolled_times")
  private int rolledTimes;

  @Column(name = "sum_rolled_number_dice")
  private int sumRolledNumberDice;

  @Column(name = "created_date")
  LocalDateTime createdTime;

  @PrePersist
  private void prePersistFunction() {
    createdTime = LocalDateTime.now();
  }
}
