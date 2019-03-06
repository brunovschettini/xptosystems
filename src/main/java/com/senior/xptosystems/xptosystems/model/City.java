package com.senior.xptosystems.xptosystems.model;



import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "city",
    //uniqueConstraints = @UniqueConstraint(columnNames = {"name", "uf_id"})
    uniqueConstraints = @UniqueConstraint(columnNames = {"ibge_code"})
)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ibge_code", nullable = false)
    private Long ibge_code;

    @Column(nullable = false, length = 255, columnDefinition = "varchar(255) default ''")
    private String name;

    @JoinColumn
    @OneToMany
    private Uf uf;

    @Column(nullable = false)
    private Boolean capital;

    @Column(nullable = false, precision = 12, scale = 10)
    private BigDecimal lon;

    @Column(nullable = false, precision = 12, scale = 10)
    private BigDecimal lat;

    public City() {
        // this.uf = null;

    }

    public City(Long id, Long ibge_code, String name, Uf uf, Boolean capital, BigDecimal lon, BigDecimal lat) {
        this.id = id;
        this.ibge_code = ibge_code;
        this.name = name;
        this.uf = uf;
        this.capital = capital;
        this.lon = lon;
        this.lat = lat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIbge_code() {
        return ibge_code;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

    public void setIbge_code(Long ibge_code) {
        this.ibge_code = ibge_code;
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

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
    //    @JoinColumn(nullable = false)
//    @ManyToOne
//    private Uf uf;
//    @Column(nullable = false)
//    private Boolean capital;
//    @Column(nullable = false, precision = 3, scale = 10, columnDefinition = "DECIMAL(3,10) DEFAULT 0")
//    private BigDecimal lon;
//    @Column(nullable = false, precision = 3, scale = 10, columnDefinition = "DECIMAL(3,10) DEFAULT 0")
//    private BigDecimal lat;
//    @Column(name = "no_accents", nullable = false)
//    private String noAccents;
//    @Column(name = "alternative_names", nullable = false)
//    private String alternativeNames;
//    @JoinColumn(nullable = false)
//    @ManyToOne
//    private Microregion microregions;
//    @JoinColumn(nullable = false)
//    @ManyToOne
//    private Mesoregion mesoregions;
//    @Column(name = "created_at", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;

//    public City() {
//
//    }
//
//    public City(Long id, String ibgeId, String name, Uf uf, Boolean capital, BigDecimal lon, BigDecimal lat, String noAccents, String alternativeNames, Microregion microregions, Mesoregion mesoregions, Date createdAt) {
//        this.id = id;
//        this.ibgeId = ibgeId;
//        this.name = name;
//        this.uf = uf;
//        this.capital = capital;
//        this.lon = lon;
//        this.lat = lat;
//        this.noAccents = noAccents;
//        this.alternativeNames = alternativeNames;
//        this.microregions = microregions;
//        this.mesoregions = mesoregions;
//        this.createdAt = createdAt;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getIbgeId() {
//        return ibgeId;
//    }
//
//    public void setIbgeId(String ibgeId) {
//        this.ibgeId = ibgeId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Uf getUf() {
//        return uf;
//    }
//
//    public void setUf(Uf uf) {
//        this.uf = uf;
//    }
//
//    public Boolean getCapital() {
//        return capital;
//    }
//
//    public void setCapital(Boolean capital) {
//        this.capital = capital;
//    }
//
//    public BigDecimal getLon() {
//        return lon;
//    }
//
//    public void setLon(BigDecimal lon) {
//        this.lon = lon;
//    }
//
//    public BigDecimal getLat() {
//        return lat;
//    }
//
//    public void setLat(BigDecimal lat) {
//        this.lat = lat;
//    }
//
//    public String getNoAccents() {
//        return noAccents;
//    }
//
//    public void setNoAccents(String noAccents) {
//        this.noAccents = noAccents;
//    }
//
//    public String getAlternativeNames() {
//        return alternativeNames;
//    }
//
//    public void setAlternativeNames(String alternativeNames) {
//        this.alternativeNames = alternativeNames;
//    }
//
//    public Microregion getMicroregions() {
//        return microregions;
//    }
//
//    public void setMicroregions(Microregion microregions) {
//        this.microregions = microregions;
//    }
//
//    public Mesoregion getMesoregions() {
//        return mesoregions;
//    }
//
//    public void setMesoregions(Mesoregion mesoregions) {
//        this.mesoregions = mesoregions;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
}
