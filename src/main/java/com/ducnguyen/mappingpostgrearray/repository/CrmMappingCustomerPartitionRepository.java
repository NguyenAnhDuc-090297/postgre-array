package com.ducnguyen.mappingpostgrearray.repository;

import com.ducnguyen.mappingpostgrearray.entity.CrmMappingCustomerPartition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrmMappingCustomerPartitionRepository extends JpaRepository<CrmMappingCustomerPartition, Long> {
}