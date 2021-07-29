package com.avalog.dicedistributionsimulation.mapper;

import com.avalog.dicedistributionsimulation.dto.IProbabilityDistribution;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProbabilityDistributionMapper {

  List<ProbabilityDistributionDto> toProbabilityDistributionDtoList(List<IProbabilityDistribution> relativeDistributionList);

  ProbabilityDistributionDto toProbabilityDistributionDto(IProbabilityDistribution relativeDistribution);
}
