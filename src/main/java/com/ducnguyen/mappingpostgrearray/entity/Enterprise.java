package com.ducnguyen.mappingpostgrearray.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "enterprise", schema = "vnpt_dev")
@Getter
@Setter
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "province_id")
    private Long provinceId;
}
