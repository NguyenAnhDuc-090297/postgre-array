package com.ducnguyen.mappingpostgrearray.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DataPartitionDto {

    private Long id;

    private Long parentId;

    private String name;

//    private Long listAmId;
//
//    private Integer amPermission;
}
