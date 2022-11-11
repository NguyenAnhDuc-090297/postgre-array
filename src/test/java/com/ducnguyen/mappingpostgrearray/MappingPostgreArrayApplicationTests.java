package com.ducnguyen.mappingpostgrearray;

import com.ducnguyen.mappingpostgrearray.dto.DataPartitionDto;
import com.ducnguyen.mappingpostgrearray.entity.CrmDataPartition;
import com.ducnguyen.mappingpostgrearray.entity.TestTableNew;
import com.ducnguyen.mappingpostgrearray.entity.User;
import com.ducnguyen.mappingpostgrearray.repository.*;
import com.ducnguyen.mappingpostgrearray.util.SpringContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ObjectUtils;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest
class MappingPostgreArrayApplicationTests {

//    @Autowired
//    private UserRepository userRepo;
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

    @Test
    void contextLoads() {
    }

//    @Test
//    void persistUser() {
//        User user = new User();
////        user.setId(1L);
//
//        Long[] longArr = new Long[]{2L, 1L, 5L};
//        user.setChildId(longArr);
//
//        userRepository.save(user);
//    }

//    void getOne(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//
//    }

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
        if (obj.isPresent()) {
//            Long[] childId = obj.get().getChildId();
            System.out.println(Arrays.toString(obj.get().getChildId()));
        }
    }

    @Test
    void getDataPartition() throws SQLException {
//        List<Long> list = dataPartitionRepository.findByEnterPriseId(2001L);
//
        CrmDataPartitionRepository crmDataPartitionRepository = SpringContext.getBean(CrmDataPartitionRepository.class);
//        System.out.println(list);
        List<CrmDataPartition> list = crmDataPartitionRepository.findByEnterPriseIdEnhanced(2001L, 1L);
//        List<CrmDataPartition> list = dataPartitionRepository.listEnhanced(2001L);
        for (CrmDataPartition data : list) {
            System.out.println("List admin id: " + Arrays.asList(data.getLstAdminId()));
            System.out.println("List AM id: " + Arrays.asList(data.getLstAmId()));
            System.out.println("List permission: "+ data.getAmPermission());
            System.out.println("List id: "+ data.getId());
        }
    }

    @Test
    void getPermission() {
        Integer permission = dataPartitionRepository.getPermission(2001L, 1L);
        if (ObjectUtils.isEmpty(permission)) {
            permission = 0;
        }
        System.out.println(permission);
    }
}
