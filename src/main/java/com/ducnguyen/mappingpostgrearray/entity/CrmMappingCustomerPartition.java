package com.ducnguyen.mappingpostgrearray.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "crm_mapping_customer_partition")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CrmMappingCustomerPartition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "partition_id")
    private Long partitionId;

    @Column(name = "lst_enterprise_id")
    @Type(type = "long-array")
    private Long[] listEnterpriseId;
}
