package com.ducnguyen.mappingpostgrearray.repository;

import com.ducnguyen.mappingpostgrearray.entity.HistoryLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryLoginRepository extends JpaRepository<HistoryLogin, Long> {

    @Query("SELECT h.id FROM HistoryLogin h")
    List<Long> getListId();
}