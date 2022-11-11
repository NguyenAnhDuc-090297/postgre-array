package com.ducnguyen.mappingpostgrearray.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "history_login", schema = "vnpt_dev")
@Getter
@Setter
public class HistoryLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

}
