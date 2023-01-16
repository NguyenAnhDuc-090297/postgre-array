package com.ducnguyen.mappingpostgrearray.entity;

import com.vladmihalcea.hibernate.type.array.LongArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "crm_data_partition", schema = "vnpt_dev")
public class CrmDataPartition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(name = "lst_admin_id", columnDefinition = "Long[]")
//    @Type(type = "long-array")
//    private Long[] lstAdminId;

//    @Column(name = "lst_am_id", columnDefinition = "Long[]")
//    @Type(type = "long-array")
//    private Long[] lstAmId;
//
//    @Column(name = "lst_customer_id", columnDefinition = "Long[]")
//    @Type(type = "long-array")
//    private Long[] lstCustomerId;

//    @Column(name = "am_permission")
//    private Integer amPermission;
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(nullable = false)
    private Integer amPermission;
}
