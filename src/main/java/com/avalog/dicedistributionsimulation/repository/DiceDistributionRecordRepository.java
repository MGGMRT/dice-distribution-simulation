package com.avalog.dicedistributionsimulation.repository;


import com.avalog.dicedistributionsimulation.dto.IProbabilityDistribution;
import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface DiceDistributionRecordRepository
    extends CrudRepository<DiceDistributionRecord, Long> {

    final String QUERY =
            "SELECT "
                    + "sum_rolled_number_dice as sumOfRolledDice, SUM(rolled_times) as times, "
                    + "CONCAT(ROUND((SUM(rolled_times) * 1.0/(SELECT SUM(number_of_rolls) FROM simulation)) * 100 , 2 ),'%' ) as percent "
                    + "FROM DICE_DISTRIBUTION_RECORD  "
                    + "GROUP BY sum_rolled_number_dice "
                    + "ORDER BY 1;";

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Query(value = QUERY, nativeQuery = true)
    Collection<IProbabilityDistribution> findRelativeDistribution();
}
