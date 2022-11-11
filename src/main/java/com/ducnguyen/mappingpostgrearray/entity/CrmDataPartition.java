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
@Table(name = "test_table_new", schema = "vnpt_dev")
@TypeDefs({
        @TypeDef(
                name = "long-array",
                typeClass = LongArrayType.class
        )
})
public class CrmDataPartition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "lst_admin_id", columnDefinition = "Long[]")
    @Type(type = "long-array")
    private Long[] lstAdminId;

    @Column(name = "lst_am_id", columnDefinition = "Long[]")
    @Type(type = "long-array")
    private Long[] lstAmId;

    @Column(name = "lst_customer_id", columnDefinition = "Long[]")
    @Type(type = "long-array")
    private Long[] lstCustomerId;

    @Column(name = "am_permission")
    private Integer amPermission;
}
