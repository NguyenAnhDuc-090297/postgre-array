package com.ducnguyen.mappingpostgrearray;

import com.ducnguyen.mappingpostgrearray.batch.BatchExecutor;
import com.ducnguyen.mappingpostgrearray.dto.MappingPartitionEnterpriseDto;
import com.ducnguyen.mappingpostgrearray.entity.*;
import com.ducnguyen.mappingpostgrearray.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class MappingPostgreArrayApplicationTests {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    EnterpriseRepository enterpriseRepo;

    @Autowired
    JdbcTemplate template;

    @Autowired
    TestTableRepository testTableRepo;

    @Autowired
    HistoryLoginRepository historyLoginRepo;

    @Autowired
    CrmDataPartitionRepository dataPartitionRepository;

    @Autowired
    CrmMappingCustomerPartitionRepository mappingCustomerPartitionRepo;

    @Autowired
    MappingPartitionEnterpriseRepository partitionEnterpriseRepo;

    @Value("${spring.hibernate.}")

    @Test
    void contextLoads() {
    }

    @Test
    void persistUser() {
        TestTableNew tableNew = new TestTableNew();

        Long[] longArr = new Long[]{20000000000000L, 100000000000000L, 5L};
        tableNew.setChildId(longArr);

        testTableRepo.save(tableNew);
    }

    @Test
    void getOne(Long id) {
//        User user = userRepository.findById(id).orElse(null);

    }

    @Test
    void setDataForTest(){

//        List<Long> listEnterpriseId = enterpriseRepo.getListEnterpriseId();
//        Long[] vs = listEnterpriseId.toArray(new Long[0]);
        List<Long> listId = new LinkedList<>();
        for (int i = 1; i <= 15; i++) {
            listId.add((long) i);
        }

        Long[] vs = listId.toArray(Long[]::new);
        TestTableNew testTableNew = new TestTableNew();
        testTableNew.setChildId(vs);

        testTableRepo.save(testTableNew);
    }

    @Test
    void testListToArray() {
        Set<Long> itemLong = new HashSet<>();
        itemLong.add(1L);
        itemLong.add(2L);
        itemLong.add(4L);

//        Long[] longArr = new Long[itemLong.size()];
//        longArr = itemLong.toArray(longArr);
        Long[] longArr = itemLong.toArray(Long[]::new);
        System.out.println(Arrays.toString(longArr));
    }

    @Test
    void getDataForTest(){

        Optional<TestTableNew> obj = testTableRepo.findById(10L);
        obj.ifPresent(testTableNew -> System.out.println(Arrays.toString(testTableNew.getChildId())));
    }

    @Test
    void getDataPartition() throws SQLException {
//        List<Long> list = dataPartitionRepository.findByEnterPriseId(2001L);
//
//        CrmDataPartitionRepository crmDataPartitionRepository = SpringContext.getBean(CrmDataPartitionRepository.class);
////        System.out.println(list);
//        List<CrmDataPartition> list = crmDataPartitionRepository.findByEnterPriseIdEnhanced(2001L, 1L);
////        List<CrmDataPartition> list = dataPartitionRepository.listEnhanced(2001L);
//        for (CrmDataPartition data : list) {
//            System.out.println("List admin id: " + Arrays.asList(data.getLstAdminId()));
//            System.out.println("List AM id: " + Arrays.asList(data.getLstAmId()));
//            System.out.println("List permission: "+ data.getAmPermission());
//            System.out.println("List id: "+ data.getId());
//        }
    }

    @Test
    void getPermission() {
        Integer permission = dataPartitionRepository.getPermission(2001L, 1L);
        if (ObjectUtils.isEmpty(permission)) {
            permission = 0;
        }
        System.out.println(permission);
    }

    @Test
    void insertData() {
        List<CrmMappingCustomerPartition> list = mappingCustomerPartitionRepo.findAll();
        Long[] listEnterpriseId = new Long[0];
        for (CrmMappingCustomerPartition item : list) {
            listEnterpriseId = item.getListEnterpriseId();
        }

        List<Long> listPartitionId = list.stream().map(CrmMappingCustomerPartition::getPartitionId).collect(Collectors.toList());

        List<MappingPartitionEnterprise> listMap = new LinkedList<>();
        for (Long partitionId : listPartitionId) {
            for (Long enterpriseId : listEnterpriseId) {
                MappingPartitionEnterprise mapping = new MappingPartitionEnterprise();
                mapping.setEnterpriseId(enterpriseId);
                mapping.setPartitionId(partitionId);
                listMap.add(mapping);
            }
            
        }
        System.out.println(listEnterpriseId.length);
        System.out.println(listPartitionId.size());
        System.out.println(listMap.size());
        System.out.println("ok");
        partitionEnterpriseRepo.saveAll(listMap);
    }

    @Test
    void batchInsertData() {
        List<MappingPartitionEnterpriseDto> list = new ArrayList<>();
        for (int enterpriseId = 1; enterpriseId <= 660000; enterpriseId++) {
            MappingPartitionEnterpriseDto dto = new MappingPartitionEnterpriseDto();
            dto.setEnterpriseId((long) enterpriseId);
            dto.setPartitionId(99L);
            list.add(dto);
        }
        System.out.println("Size of element" + list.size());
        BatchExecutor executor = new BatchExecutor();
        executor.batchInsert(list, 1000);
    }

    @Test
    void deletePartitionMappingData() {
        partitionEnterpriseRepo.deleteAllByPartitionId(99L);
    }
}
