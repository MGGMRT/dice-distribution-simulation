package com.avalog.dicedistributionsimulation.repository;


import com.avalog.dicedistributionsimulation.dto.ICombination;
import com.avalog.dicedistributionsimulation.model.Simulation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface SimulationRepository extends CrudRepository<Simulation, Long> {

    final String QUERY =
            "SELECT "
                    + " s.dice_count as numOfDice, s.dice_side as numOfDiceSide, SUM(ddr.sum_rolled_number_dice ) as sumOfRolledDice, "
                    + " COUNT(DISTINCT s.simulation_id) as numOfSimulation "
                    + " FROM simulation as s  INNER JOIN DICE_DISTRIBUTION_RECORD as ddr "
                    + " on s.simulation_id = ddr.simulation_id "
                    + " GROUP BY  s.dice_count, s.dice_side; ";

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Query(value = QUERY, nativeQuery = true)
    Collection<ICombination> findSimulationByDiceCountAndDiceSide();
}
