package com.ducnguyen.mappingpostgrearray.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@RequiredArgsConstructor
public class TreeRecursive {

    private Long id;
    private String name;
    private String code;
    private List<TreeRecursive> listChild;
}
