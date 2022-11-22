package com.ducnguyen.mappingpostgrearray.repository;

import com.ducnguyen.mappingpostgrearray.dto.DataPartitionDto;
import com.ducnguyen.mappingpostgrearray.entity.CrmDataPartition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CrmDataPartitionRepository extends JpaRepository<CrmDataPartition, Long> {

    @Query(value = "SELECT unnest(dp.lst_admin_id) as lst_admin_id FROM vnpt_dev.crm_data_partition dp WHERE :enterpriseId = any(dp.lst_customer_id)", nativeQuery = true)
    List<Long> findByEnterPriseId(Long enterpriseId);

//    @Query(value = "SELECT unnest(dp.lst_admin_id) as lst_admin_id FROM vnpt_dev.crm_data_partition dp WHERE :enterpriseId = any(dp.lst_customer_id)", nativeQuery = true)
//    List<Long> findByEnterPriseId(Long enterpriseId);
//
//    @Query(value = "SELECT unnest(dp.lst_admin_id) as lst_admin_id, dp.am_permission FROM vnpt_dev.crm_data_partition dp WHERE :enterpriseId = any(dp.lst_customer_id)", nativeQuery = true)
//    List<DataPartitionDto> findByEnterPriseIdEn(Long enterpriseId);
//
//    @Query(value = "SELECT * FROM vnpt_dev.crm_data_partition dp WHERE :enterpriseId = ANY(dp.lst_customer_id) AND :userId = any(dp.lst_admin_id) OR :userId = any(dp.lst_am_id) AND dp.am_permission > 1", nativeQuery = true)
//    List<CrmDataPartition> findByEnterPriseIdEnhanced(Long enterpriseId, Long userId);
//
//    @Query(value = "SELECT * FROM vnpt_dev.crm_data_partition dp WHERE :enterpriseId = ANY(dp.lst_customer_id)", nativeQuery = true)
//    List<CrmDataPartition> listEnhanced(Long enterpriseId);

    @Query(value = "SELECT \n" +
            "   MAX (CASE \n" +
            "       WHEN :userId = ANY(lst_admin_id) THEN 2 \n" +
            "       WHEN :userId = ANY(lst_am_id) THEN am_permission \n" +
            "   END) AS permission \n" +
            " FROM \n" +
            "   {h-schema}crm_data_partition \n" +
            " WHERE \n" +
            "   :enterpriseId = ANY(lst_customer_id) \n" +
            "   AND :userId = ANY(lst_admin_id) OR :userId = ANY(lst_am_id) \n" +
            "   AND status = 1 \n", nativeQuery = true)
    Integer getPermission(Long enterpriseId, Long userId);
}