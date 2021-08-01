package com.avalog.dicedistributionsimulation.repository;

import com.avalog.dicedistributionsimulation.model.DiceDistributionRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiceDistributionRecordRepository
    extends CrudRepository<DiceDistributionRecord, Long> {
}
