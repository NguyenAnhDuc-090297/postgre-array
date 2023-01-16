package com.ducnguyen.mappingpostgrearray.controller;

import com.ducnguyen.mappingpostgrearray.batch.BatchExecutor;
import com.ducnguyen.mappingpostgrearray.dto.*;
import com.ducnguyen.mappingpostgrearray.entity.CrmDataPartition;
import com.ducnguyen.mappingpostgrearray.entity.CustomLayout;
import com.ducnguyen.mappingpostgrearray.repository.CrmDataPartitionRepository;
import com.ducnguyen.mappingpostgrearray.repository.CustomLayoutRepository;
import com.ducnguyen.mappingpostgrearray.repository.EnterpriseRepository;
import com.ducnguyen.mappingpostgrearray.repository.MappingPartitionEnterpriseRepository;
import com.ducnguyen.mappingpostgrearray.tree.TreeRecursive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@RestController
@RequestMapping("/v1/test")
@Slf4j
public class TestController {

    @Autowired
    MappingPartitionEnterpriseRepository partitionEnterpriseRepository;

    @Autowired
    CustomLayoutRepository customLayoutRepository;

    @Autowired
    CrmDataPartitionRepository dataPartitionRepository;

    @Autowired
    EnterpriseRepository enterpriseRepository;

    @Autowired
    EntityManager entityManager;

    @PostMapping("/insert-batch")
    public long insertBatch(@RequestBody InsertBatchDto insertBatchDto) {
        List<MappingPartitionEnterpriseDto> list = new ArrayList<>();
        for (int enterpriseId = 1; enterpriseId <= insertBatchDto.getEnterpriseIdSize(); enterpriseId++) {
            MappingPartitionEnterpriseDto dto = new MappingPartitionEnterpriseDto();
            dto.setEnterpriseId((long) enterpriseId);
            dto.setPartitionId(insertBatchDto.getPartitionId());
            list.add(dto);
        }
        log.info("Size of elements: " + list.size());

        long startTime = System.currentTimeMillis();

        BatchExecutor executor = new BatchExecutor();
        executor.batchInsert(list, insertBatchDto.getBatchSize());

        long endTime = System.currentTimeMillis();
        return (endTime - startTime);
    }

    @DeleteMapping("/delete-all/{id}")
    public long deleteAllByPartitionId(@PathVariable Long id) {
        try {
            long startTime = System.currentTimeMillis();

            partitionEnterpriseRepository.deleteAllByPartitionId(id);

            long endTime = System.currentTimeMillis();
            return (endTime - startTime);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        }
    }

    @DeleteMapping("/delete-batch")
    public long deleteBatch(@RequestBody DeleteBatchDto deleteBatchDto) {
        try {
            List<Long> asList = deleteBatchDto.getListPartitionId();
            long startTime = System.currentTimeMillis();
            BatchExecutor executor = new BatchExecutor();
            executor.deleteBatch(asList);

            long endTime = System.currentTimeMillis();
            return (endTime - startTime);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        }
    }

    @GetMapping("/json-handle")
    public void handleJson(@RequestBody TestDto dto){
        Gson gson = new Gson();
        String data = gson.toJson(dto);

        CustomLayout customLayout = new CustomLayout();
        List<String> dataString = Collections.singletonList(data);
        log.info(dataString.toString());
        try {
            customLayout.setName(dto.getName());
            customLayout.setCategory("cat1");
            customLayout.setCreatedAt(new Date());
            customLayout.setCreatedBy(1L);
            customLayout.setLstStandardField(Collections.singletonList("test2"));
            customLayout.setDataDependency(Collections.singletonList(dataString));
            customLayoutRepository.save(customLayout);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @GetMapping("/tree")
    public TreeRecursive getTree(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Long loggedInId,
                                 @RequestParam(required = false) Long amId) throws JsonProcessingException {

        List<CrmDataPartition> list = new ArrayList<>();



        List<Object> listObject = dataPartitionRepository.findData(keyword, amId, loggedInId);
        for (Object o : listObject) {
            Object[] object = (Object[]) o;
            Long id = Long.parseLong(String.valueOf(object[0]));
            String name = (String) object[1];
            String code = (String) object[2];
            Long parentId = Long.parseLong(String.valueOf(object[3]));
            CrmDataPartition data = new CrmDataPartition();
            data.setId(id);
            data.setCode(code);
            data.setName(name);
            data.setParentId(parentId);
            list.add(data);
        }

        TreeRecursive recursive = new TreeRecursive();

        List<TreeRecursive> listElement = new ArrayList<>();
        for (CrmDataPartition single : list) {
            if (single.getParentId() == -1) {
                TreeRecursive element = new TreeRecursive();
                element.setId(single.getId());
                element.setName(single.getName());
                element.setCode(single.getCode());
                listElement.add(element);
            }
        }
        recursive.setListChild(listElement);

        this.recursive(recursive, list);
        return recursive;
    }

    private void recursive(TreeRecursive recursive, List<CrmDataPartition> dataPartitionList){
        List<TreeRecursive> listChild = recursive.getListChild();
        for (TreeRecursive element : listChild) {
            List<TreeRecursive> childElements = new ArrayList<>();
            for (CrmDataPartition partition : dataPartitionList) {
                if (Objects.equals(element.getId(), partition.getParentId())) {
                    TreeRecursive newChild = new TreeRecursive();
                    newChild.setId(partition.getId());
                    newChild.setName(partition.getName());
                    newChild.setCode((partition.getCode()));
                    childElements.add(newChild);
                }
            }
            element.setListChild(childElements);
        }
        List<TreeRecursive> child = recursive.getListChild();
        child.forEach(childE -> recursive(childE, dataPartitionList));
    }

    @GetMapping("/getId")
    public List<Long> getId() {
        try {
            List<Long> id = new ArrayList<>(72);
            Long[] arr = id.toArray(new Long[0]);
            List<Long> ids =  dataPartitionRepository.getIdTest(arr, 1L);
            return ids;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @GetMapping("/getIdTest1")
    public Page<Long> getId1(@RequestParam Integer pageNo) {
        try {
            Pageable pageable = PageRequest.of(pageNo, 10);
            Page<Long> ids =  dataPartitionRepository.getListId(pageable);
            return ids;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @GetMapping("/getIdTest2")
    public Page<Long> getId2(@RequestParam Integer pageNo) {
        try {
            Pageable pageable = PageRequest.of(pageNo, 10);
            Page<Long> ids =  dataPartitionRepository.getListId2(pageable);
            return ids;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
