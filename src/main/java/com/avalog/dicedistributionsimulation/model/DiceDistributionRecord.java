package com.avalog.dicedistributionsimulation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "dice_distribution_record")
public class DiceDistributionRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int Id;

  @Column(name = "rolled_times")
  private Long rolledTimes;

  @Column(name = "sum_rolled_number_dice")
  private int sumRolledNumberDice;

  @Column(name = "created_date")
  private LocalDateTime createdTime;

  @ManyToOne
  @JoinColumn(name = "simulation_Id", nullable = false)
  private Simulation simulation;

  @PrePersist
  private void prePersistFunction() {
    createdTime = LocalDateTime.now();
  }
}
