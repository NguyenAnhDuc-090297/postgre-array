package com.ducnguyen.mappingpostgrearray.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
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
public class TestTableNew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "enterprise_id", columnDefinition = "Long[]")
//    @Type(type = "com.ducnguyen.mappingpostgrearray.entity.helper.GenericArrayUserType")
    @Type(type = "long-array")
    private Long[] childId;
}
