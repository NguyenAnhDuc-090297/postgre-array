package com.ducnguyen.mappingpostgrearray.repository;

import java.util.Set;
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

    @Query(value = "SELECT \n"
        + "     mPartition.id\n"
        + " FROM {h-schema}crm_data_partition AS mPartition \n"
        + "     LEFT JOIN {h-schema}crm_data_condition AS mCondition \n"
        + "         ON mCondition.partition_id = mPartition.id AND mCondition.object_type = :objectType \n"
        + " WHERE \n"
        + "     mPartition.status = 1 AND \n"
        + "     (mCondition.condition_query IS NOT NULL AND \n"
        + "         {h-schema}func_check_object_match_condition(:objectType, :objectId, mCondition.condition_query)) \n", nativeQuery = true)
    Set<Long> listPartitionIdMatchedObjectAndCondition(Integer objectType, Long objectId);
}