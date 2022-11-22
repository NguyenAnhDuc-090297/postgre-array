package com.ducnguyen.mappingpostgrearray.repository;

import com.ducnguyen.mappingpostgrearray.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    @Query("SELECT e.id FROM Enterprise e")
    List<Long> getListEnterpriseId();

    @Query("SELECT e.id FROM Enterprise e WHERE e.id < 500000")
    List<Long> getListEnterpriseIdLessThan500k();

    @Query("SELECT id FROM Enterprise WHERE provinceId = 21")
    List<Long> getEnterpriseId();
}
