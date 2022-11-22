package com.ducnguyen.mappingpostgrearray.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class DeleteBatchDto {

    private List<Long> listPartitionId;
}
