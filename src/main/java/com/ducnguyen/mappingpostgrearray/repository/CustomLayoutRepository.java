package com.ducnguyen.mappingpostgrearray.repository;

import com.ducnguyen.mappingpostgrearray.entity.CustomLayout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomLayoutRepository extends JpaRepository<CustomLayout, Long> {
}