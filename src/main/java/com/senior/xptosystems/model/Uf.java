package com.senior.xptosystems.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Uf implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true, length = 2, columnDefinition = "varchar(2) default ''")
    private String name;

    public Uf() {
    }

    public Uf(Long id, String name) {
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
