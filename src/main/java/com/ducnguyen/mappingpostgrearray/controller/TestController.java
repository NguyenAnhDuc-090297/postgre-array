package com.ducnguyen.mappingpostgrearray.controller;

import com.ducnguyen.mappingpostgrearray.batch.BatchExecutor;
import com.ducnguyen.mappingpostgrearray.dto.*;
import com.ducnguyen.mappingpostgrearray.entity.CrmDataPartition;
import com.ducnguyen.mappingpostgrearray.entity.CustomLayout;
import com.ducnguyen.mappingpostgrearray.repository.CrmDataPartitionRepository;
import com.ducnguyen.mappingpostgrearray.repository.CustomLayoutRepository;
import com.ducnguyen.mappingpostgrearray.repository.MappingPartitionEnterpriseRepository;
import com.ducnguyen.mappingpostgrearray.tree.TreeRecursive;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public TreeRecursive getTree() {
        List<CrmDataPartition> list = dataPartitionRepository.findAll();

        TreeRecursive recursive = new TreeRecursive();

        List<TreeRecursive> listElement = new ArrayList<>();
        for (CrmDataPartition single : list) {
            if (single.getParentId() == -1) {
                TreeRecursive element = new TreeRecursive();
                element.setId(single.getId());
                element.setName(single.getName());
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
                    childElements.add(newChild);
                }
            }
            element.setListChild(childElements);
        }
        List<TreeRecursive> child = recursive.getListChild();
        child.forEach(childE -> recursive(childE, dataPartitionList));
    }
}
