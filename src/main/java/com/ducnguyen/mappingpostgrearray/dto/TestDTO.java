package com.ducnguyen.mappingpostgrearray.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class TestDTO {

    private Set<Long> data = new HashSet<>();
}
