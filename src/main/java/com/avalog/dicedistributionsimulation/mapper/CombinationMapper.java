package com.avalog.dicedistributionsimulation.mapper;

import com.avalog.dicedistributionsimulation.dto.ICombination;
import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CombinationMapper {

  List<CombinationDto> toListSimulationStatisticsDtoList(List<ICombination> simulationList);

  CombinationDto toSimulationStatisticsDto(ICombination simulation);
}
