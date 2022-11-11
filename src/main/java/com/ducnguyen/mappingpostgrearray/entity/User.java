package com.ducnguyen.mappingpostgrearray.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "dev")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "Long[]", name = "child_id")
    @Type(type = "com.ducnguyen.mappingpostgrearray.entity.helper.MappingLongArray")
    private Long[] childId;
}
