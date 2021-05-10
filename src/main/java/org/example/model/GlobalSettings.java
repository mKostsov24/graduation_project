package org.example.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GlobalSettings {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String value;
}
