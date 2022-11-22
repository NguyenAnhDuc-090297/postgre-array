package com.ducnguyen.mappingpostgrearray.entity;

import com.ducnguyen.mappingpostgrearray.convert.ListObjectConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class CustomLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String category;

    @Convert(converter = ListObjectConverter.class)
    private List<Object> lstStandardField;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Convert(converter = ListObjectConverter.class)
    @Column(columnDefinition = "json", name = "data_dependency")
    private List<Object> dataDependency;
}
