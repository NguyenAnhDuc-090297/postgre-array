package com.ducnguyen.mappingpostgrearray.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MappingPartitionEnterpriseDto {

    private Long partitionId;

    private Long enterpriseId;
}
