package com.ducnguyen.mappingpostgrearray.repository;

import com.ducnguyen.mappingpostgrearray.entity.MappingPartitionEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;

public interface MappingPartitionEnterpriseRepository extends JpaRepository<MappingPartitionEnterprise, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from {h-schema}mapping_partition_enterprise where partition_id = :partitionId", nativeQuery = true)
    void deleteAllByPartitionId(Long partitionId);
}