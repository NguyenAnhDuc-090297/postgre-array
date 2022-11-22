package com.ducnguyen.mappingpostgrearray.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TestDto {

    private String name;
    private Integer age;
    private String address;
}
