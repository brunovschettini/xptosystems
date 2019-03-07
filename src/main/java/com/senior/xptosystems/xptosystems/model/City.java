package com.senior.xptosystems.xptosystems.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "city",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"ibge_id"})

        }
)
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn
    @Column(name = "ibge_id", nullable = false)
    private Long ibge_id;

    @JoinColumn
    @OneToOne
    private Uf uf;

    @Column(nullable = false, length = 255, columnDefinition = "varchar(255) default ''")
    private String name;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean capital;

    @Column(nullable = false, precision = 12, scale = 10, columnDefinition = "double precision default 0")
    private Double lon;

    @Column(nullable = false, precision = 12, scale = 10, columnDefinition = "double precision  default 0")
    private Double lat;

    @Column(name = "no_accents", nullable = false, length = 100, columnDefinition = "varchar(100) default ''")
    private String noAccents;
    @Column(name = "alternative_names", nullable = false, length = 100, columnDefinition = "varchar(100) default ''")
    private String alternativeNames;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Microregion microregions;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Mesoregion mesoregions;
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Transient
    private String ufName;

    @Transient
    private String microregionName;

    @Transient
    private String mesoregionName;

    public City() {
        // this.uf = null;

    }

    public City(Long id, Long ibge_code, Uf uf, String name, Boolean capital, Double lon, Double lat, String noAccents, String alternativeNames, Microregion microregions, Mesoregion mesoregions, Date createdAt) {
        this.id = id;
        this.ibge_id = ibge_code;
        this.uf = uf;
        this.name = name;
        this.capital = capital;
        this.lon = lon;
        this.lat = lat;
        this.noAccents = noAccents;
        this.alternativeNames = alternativeNames;
        this.microregions = microregions;
        this.mesoregions = mesoregions;
        this.createdAt = createdAt;
    }

    public City(Long ibge_id, String ufName, String name, Boolean capital, Double lon, Double lat, String noAccents, String alternativeNames, String microregionName, String mesoregionName) {
        this.ibge_id = ibge_id;
        this.ufName = ufName;
        this.name = name;
        this.capital = capital;
        this.lon = lon;
        this.lat = lat;
        this.noAccents = noAccents;
        this.alternativeNames = alternativeNames;
        this.microregionName = microregionName;
        this.mesoregionName = mesoregionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIbge_id() {
        return ibge_id;
    }

    public void setIbge_id(Long ibge_id) {
        this.ibge_id = ibge_id;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getNoAccents() {
        return noAccents;
    }

    public void setNoAccents(String noAccents) {
        this.noAccents = noAccents;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public Microregion getMicroregions() {
        return microregions;
    }

    public void setMicroregions(Microregion microregions) {
        this.microregions = microregions;
    }

    public Mesoregion getMesoregions() {
        return mesoregions;
    }

    public void setMesoregions(Mesoregion mesoregions) {
        this.mesoregions = mesoregions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    void preInsert() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

    public String getUfName() {
        return ufName;
    }

    public void setUfName(String ufName) {
        this.ufName = ufName;
    }

    public String getMicroregionName() {
        return microregionName;
    }

    public void setMicroregionName(String microregionName) {
        this.microregionName = microregionName;
    }

    public String getMesoregionName() {
        return mesoregionName;
    }

    public void setMesoregionName(String mesoregionName) {
        this.mesoregionName = mesoregionName;
    }

}
