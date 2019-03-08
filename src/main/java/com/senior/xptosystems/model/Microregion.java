package com.senior.xptosystems.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Microregion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100, columnDefinition = "varchar(100) default ''")
    private String name;

    public Microregion() {
    }

    public Microregion(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
