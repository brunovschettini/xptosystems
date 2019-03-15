package com.senior.xptosystems.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import org.springframework.data.geo.Point;

@Entity
@Table(
        name = "city",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"ibge_id"})

        }
)
@NamedNativeQueries({
    @NamedNativeQuery(name = "City.countByUfs", query = "SELECT count(*) FROM city C GROUP BY C.uf_id")
    ,@NamedNativeQuery(name = "City.countByName", query = "SELECT count(*) FROM city C GROUP BY C.name")
    ,@NamedNativeQuery(name = "City.countByNoAccents", query = "SELECT count(*) FROM city C GROUP BY C.no_accents")
    ,@NamedNativeQuery(name = "City.countByAlternativeNames", query = "SELECT count(*) FROM city C GROUP BY C.alternative_names")
    ,@NamedNativeQuery(name = "City.countByMicrorgions", query = "SELECT count(*) FROM city C GROUP BY C.microregions_id")
    ,@NamedNativeQuery(name = "City.countByMesoregions", query = "SELECT count(*) FROM city C GROUP BY C.mesoregions_id")
})
@NamedQueries({
    @NamedQuery(name = "City.fetchByLat", query = "SELECT C FROM City C WHERE C.lat >= :queryParam ORDER BY C.lat ASC")
    ,@NamedQuery(name = "City.fetchByLon", query = "SELECT C FROM City C WHERE C.lon >= :queryParam ORDER BY C.lon ASC")
})

public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn
    @Column(name = "ibge_id", nullable = false)
    private Long ibgeId;

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

    @Column(name = "location_point")
    private Point locationPoint;

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
        this.ibgeId = ibgeId;
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
        this.locationPoint = new Point(lat, lon);
    }

    public City(Long ibgeId, String ufName, String name, Boolean capital, Double lon, Double lat, String noAccents, String alternativeNames, String microregionName, String mesoregionName) {
        this.ibgeId = ibgeId;
        this.ufName = ufName;
        this.name = name;
        this.capital = capital;
        this.lon = lon;
        this.lat = lat;
        this.noAccents = noAccents;
        this.alternativeNames = alternativeNames;
        this.microregionName = microregionName;
        this.mesoregionName = mesoregionName;
        this.locationPoint = new Point(lat, lon);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIbgeId() {
        return ibgeId;
    }

    public void setIbgeId(Long ibgeId) {
        this.ibgeId = ibgeId;
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

    public Point getLocationPoint() {
        return locationPoint;
    }

    public void setLocationPoint(Point locationPoint) {
        this.locationPoint = locationPoint;
    }

    @Override
    public String toString() {
        return "City{" + "id=" + id + ", ibgeId=" + ibgeId + ", uf=" + uf + ", name=" + name + ", capital=" + capital + ", lon=" + lon + ", lat=" + lat + ", locationPoint=" + locationPoint + ", noAccents=" + noAccents + ", alternativeNames=" + alternativeNames + ", microregions=" + microregions + ", mesoregions=" + mesoregions + ", createdAt=" + createdAt + '}';
    }

}
