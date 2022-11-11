package com.ducnguyen.mappingpostgrearray.controller;

import com.ducnguyen.mappingpostgrearray.entity.User;
import com.ducnguyen.mappingpostgrearray.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id) {
        User user = userService.getOne(id);
        Long[] childId = user.getChildId();
        List<Long> childIdList = new ArrayList<>(List.of(childId));
        for (Long singleChild : childIdList) {
            log.info(singleChild.toString());
        }
        return ResponseEntity.ok(user);
    }
}
