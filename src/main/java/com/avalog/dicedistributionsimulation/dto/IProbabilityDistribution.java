package com.avalog.dicedistributionsimulation.dto;

public interface IProbabilityDistribution {
  int getSumOfRolledDice();

  int getTimes();

  String getPercent();
}
