package com.avalog.dicedistributionsimulation.repository;

import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.model.Simulation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SimulationRepository extends CrudRepository<Simulation, Long> {

  @Transactional(isolation = Isolation.REPEATABLE_READ)
  @Query(
      value =
          "SELECT new com.avalog.dicedistributionsimulation.dto.response.CombinationDto("
              + "COUNT(s.Id), SUM(ddr.sumRolledNumberDice), s.diceCount, s.diceSides ) "
              + "FROM simulation s JOIN s.diceDistributionRecordSet ddr "
              + "GROUP BY s.diceCount, s.diceSides")
  List<CombinationDto> getSimulationAggByDiceCountAndDiceSide();

  @Transactional(isolation = Isolation.REPEATABLE_READ)
  @Query(
      value =
          "SELECT new com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto("
              + "ddr.sumRolledNumberDice, SUM(ddr.rolledTimes), CONCAT(ROUND(((SUM(ddr.rolledTimes) * 1.0)/SUM(s.numberOfRolls))*100,2),'%') ) "
              + "FROM simulation s JOIN s.diceDistributionRecordSet ddr "
              + "GROUP BY ddr.sumRolledNumberDice ORDER BY ddr.sumRolledNumberDice ")
  List<ProbabilityDistributionDto> getSimulationProbability();

  // CONCAT(ROUND((SUM(ddr.rolledTimes) * 1.0/(SELECT SUM(s.numberOfRolls) FROM simulation as s)) *
  // 100 , 2 ) ,'%' )
}
