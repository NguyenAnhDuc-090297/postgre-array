package com.ducnguyen.mappingpostgrearray.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "mapping_partition_enterprise")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MappingPartitionEnterprise {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "mp_partition_enterprise_seq", initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long partitionId;

    private Long enterpriseId;
}
