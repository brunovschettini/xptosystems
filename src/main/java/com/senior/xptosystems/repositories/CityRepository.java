package com.senior.xptosystems.repositories;

import com.senior.xptosystems.model.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT C FROM City C WHERE C.capital = 1 ORDER BY C.name")
    List<City> findByCapitalOrderByNameAsc();

    @Query("SELECT C FROM City C WHERE C.ibge_id = ?1")
    City findByIbge_id(Long ibge_id);

    @Query("SELECT C FROM City C WHERE C.uf.id = ?1 ORDER BY C.name")
    List<City> findByUf_if(Long uf_if);

    @Query(
            value = " "
            + "(\n"
            + " SELECT \n"
            + "    'bigger' AS ufcounts, \n"
            + "    u.name, count(*) city_count\n"
            + "    FROM city C\n"
            + "    INNER JOIN Uf AS u ON u.id = C.uf_id \n"
            + "    GROUP BY u.name \n"
            + "    ORDER BY count(*) ASC \n"
            + "    LIMIT 1\n"
            + ") \n"
            + "UNION ALL\n"
            + "(\n"
            + "    SELECT \n"
            + "    'smaller' AS ufcounts,\n"
            + "    u.name, count(*) city_count\n"
            + "    FROM city C\n"
            + "    INNER JOIN Uf AS u ON u.id = C.uf_id \n"
            + "    GROUP BY u.name \n"
            + "    ORDER BY count(*) DESC \n"
            + "    LIMIT 1 \n"
            + " )",
            nativeQuery = true
    )
    List findMinMaxCitiesByUf();

    @Query(value = "SELECT count(*) \n"
            + "FROM city C\n"
            + "WHERE C.uf_id = ?1",
            nativeQuery = true)
    Integer findCountByUf(Long uf_id);

    @Query(value = "SELECT count(*) FROM city",
            nativeQuery = true)
    Integer total();

}
