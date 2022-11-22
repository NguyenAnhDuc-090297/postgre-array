package com.ducnguyen.mappingpostgrearray.batch;

import com.ducnguyen.mappingpostgrearray.dto.MappingPartitionEnterpriseDto;
import com.ducnguyen.mappingpostgrearray.util.SpringContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BatchExecutor {

    private final JdbcTemplate jdbcTemplate = SpringContext.getBean(JdbcTemplate.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate = SpringContext.getBean(NamedParameterJdbcTemplate.class);

    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<MappingPartitionEnterpriseDto> partitionEnterpriseList, int batchSize) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO vnpt_dev.mapping_partition_enterprise (partition_id, enterprise_id) VALUES (?, ?) ",
                partitionEnterpriseList, batchSize,
                (ps, argument) -> {
                    ps.setLong(1, argument.getPartitionId());
                    ps.setLong(2, argument.getEnterpriseId());
                });
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> listPartitionId) {
        String sqlQuery = "delete from vnpt_dev.mapping_partition_enterprise where partition_id in (:listPartitionId)";
        Map<String, List<Long>> namedParameters = Collections.singletonMap("listPartitionId", listPartitionId);
        namedParameterJdbcTemplate.update(sqlQuery, namedParameters);
    }
}
