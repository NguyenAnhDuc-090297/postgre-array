package com.ducnguyen.mappingpostgrearray.controller;

import com.ducnguyen.mappingpostgrearray.entity.TestTableNew;
import com.ducnguyen.mappingpostgrearray.entity.User;
import com.ducnguyen.mappingpostgrearray.repository.TestTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/v1/test")
@Slf4j
public class TableController {

    @Autowired
    private TestTableRepository tableRepo;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
//        Object data = tableRepo.getLongArray(id);
//        return ResponseEntity.ok(data);
        Optional<TestTableNew> obj = tableRepo.findById(id);
        if (obj.isPresent()){
            Long[] childId = obj.get().getChildId();
            log.info(Arrays.toString(childId));
            return ResponseEntity.ok(childId);
        }
        return ResponseEntity.ok(new Long[1]);
    }
}
