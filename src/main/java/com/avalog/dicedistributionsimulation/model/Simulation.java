package com.avalog.dicedistributionsimulation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "simulation")
public class Simulation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int Id;

  @Column(name = "number_of_rolls")
  private int numberOfRolls;

  @Column(name = "dice_sides")
  private int diceSides;

  @Column(name = "dice_count")
  private int diceCount;

  @Column(name = "created_date")
  private LocalDateTime createdTime;

  @OneToMany(mappedBy = "simulation",cascade = CascadeType.ALL,orphanRemoval = true)
  private Set<DiceDistributionRecord> diceDistributionRecordSet;

  @PrePersist
  private void prePersistFunction() {
    createdTime = LocalDateTime.now();
  }
}
