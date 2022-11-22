package com.ducnguyen.mappingpostgrearray.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InsertBatchDto {

    private Long partitionId;
    private Integer batchSize;
    private Integer enterpriseIdSize;
}
