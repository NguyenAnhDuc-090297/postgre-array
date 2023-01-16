package com.ducnguyen.mappingpostgrearray.repository;

import com.ducnguyen.mappingpostgrearray.dto.DataPartitionDto;
import com.ducnguyen.mappingpostgrearray.entity.CrmDataPartition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(nativeQuery = true, value = " SELECT * FROM {h-schema}crm_data_partition dp where (:userId) = ANY(dp.lst_admin_id) ")
    List<CrmDataPartition> findAllByCurrentUser(Long userId);

    @Query(nativeQuery = true, value = "WITH RECURSIVE tmp AS ( \n" +
            " SELECT id \n" +
            "   FROM vnpt_dev.crm_data_partition \n" +
            "   WHERE id IN ( \n" +
            "       SELECT id \n" +
            "           FROM vnpt_dev.crm_data_partition \n" +
            "           where \n" +
            "           \t(:loggedInId = ANY(crm_data_partition.lst_admin_id) OR :loggedInId = ANY(crm_data_partition.lst_am_id))\n" +
            "           \tAND (:amId is null OR (:amId = ANY(crm_data_partition.lst_admin_id) OR :amId = ANY(crm_data_partition.lst_am_id)))\n" +
            "   \t\t\tAND (('' = :keyword OR crm_data_partition.name ILIKE ('%' || :keyword || '%')) \n" +
            "                   OR ('' = :keyword OR crm_data_partition.code ILIKE ('%' || :keyword || '%'))))\n" +
            "            and parent_id <> -1\n" +
            "       UNION ALL \n" +
            "           SELECT crm_data_partition.id \n" +
            "               FROM vnpt_dev.crm_data_partition \n" +
            "               JOIN tmp ON tmp.id = crm_data_partition.parent_id \n" +
            " ) \n" +
            "SELECT id, name, code, parent_id \n" +
            " FROM vnpt_dev.crm_data_partition\n" +
            " WHERE \n" +
            "   crm_data_partition.id IN (SELECT id FROM tmp)")
    List<Object> findData(String keyword, Long amId, Long loggedInId);

//    @Query(value = "SELECT id FROM CrmDataPartition WHERE :loggedInId = ANY(lstAdminId) OR :loggedInId = ANY(lstAmId)")
//    List<Long> findActuallyRootIds(Long loggedInId);

    @Query(value = "SELECT enterprise.id "
        + " FROM enterprise "
        + " JOIN crm_mapping_enterprise_partition on enterprise.id = crm_mapping_enterprise_partition.enterprise_id "
        + " WHERE "
        + "   :array && crm_mapping_enterprise_partition.lst_partition_id AND "
        + "   enterprise.id = :enterpriseId ", nativeQuery = true)
    List<Long> getIdTest(Long[] array, Long enterpriseId);

    @Query(value = "SELECT enterprise.id \n"
        + "FROM vnpt_dev.enterprise \n"
        + "WHERE \n"
        + " (( (enterprise.province_name in ('Hà Nội'))) OR ( (enterprise.province_name in ('TP Vinh'))) )", nativeQuery = true)
    Page<Long> getListId(Pageable pageable);

    @Query(value = "SELECT enterprise.id \n"
        + "FROM vnpt_dev.enterprise \n"
        + "WHERE \n"
        + "     (( (enterprise.province_id in (select id from vnpt_dev.province where name in ('Hà Nội')))) \n"
        + "     OR ( (enterprise.province_id in (select id from vnpt_dev.province where name in ('TP Vinh')))) )", nativeQuery = true)
    Page<Long> getListId2(Pageable pageable);
}