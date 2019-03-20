package com.senior.xptosystems.repositories;

import com.senior.xptosystems.model.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT C FROM City C WHERE C.capital = true ORDER BY C.name")
    List<City> capital();

    City findByIbgeId(Long ibgeId);

    List<City> findByIdIn(List<Long> ids);

    @Query("SELECT C FROM City C WHERE C.state.id = ?1 ORDER BY C.name")
    List<City> findByState(Long id);

    @Query(
            value = " "
            + "(\n"
            + " SELECT \n"
            + "    'bigger' AS aliasName, \n"
            + "    sta.name, count(*) cityCount\n"
            + "    FROM city C\n"
            + "    INNER JOIN State AS sta ON sta.id = C.state_id \n"
            + "    GROUP BY sta.name \n"
            + "    ORDER BY count(*) ASC \n"
            + "    LIMIT 1\n"
            + ") \n"
            + "UNION ALL\n"
            + "(\n"
            + "    SELECT \n"
            + "    'smaller' AS aliasName,\n"
            + "    sta.name, count(*) cityCount\n"
            + "    FROM city C\n"
            + "    INNER JOIN State AS sta ON sta.id = C.state_id \n"
            + "    GROUP BY sta.name \n"
            + "    ORDER BY count(*) DESC \n"
            + "    LIMIT 1 \n"
            + " )",
            nativeQuery = true
    )
    List statesBiggerAndSmallerNumberOfCities();

    @Query(value = "SELECT count(*) \n"
            + "FROM city C\n"
            + "WHERE C.state_id = ?1",
            nativeQuery = true)
    Integer numberOfCitiesByState(Long state_id);

    @Query(value = "SELECT count(*) FROM city",
            nativeQuery = true)
    Integer countAll();

    @Query(value = ""
            + "            SELECT CONCAT(A.name, '/', sta1.name) AS from_city, CONCAT(B.name, '/', sta2.name) AS to_city, \n"
            + "               111.111 *\n"
            + "                DEGREES(ACOS(LEAST(COS(RADIANS(a.lat))\n"
            + "                     * COS(RADIANS(b.lat))\n"
            + "                     * COS(RADIANS(a.lon- b.lon))\n"
            + "                     + SIN(RADIANS(a.lat))\n"
            + "                     * SIN(RADIANS(b.lat)), 1.0))) AS distance_in_km\n"
            + "              FROM city AS a\n"
            + "              INNER JOIN State AS sta1 ON sta1.id = A.state_id \n"
            + "              INNER JOIN city AS b ON a.id <> b.id\n"
            + "              INNER JOIN State AS sta2 ON sta2.id = B.state_id \n"
            + "             WHERE a.id = (select c3.id from city c3 where c3.location_point in (select max(location_point) as max from city)) AND b.id = (select id from city where location_point in (select min(location_point) as max from city))"
            + "", nativeQuery = true)
    List twoCitiesMoreDistant();

    @Query(value = "SELECT * FROM city C INNER JOIN State sta ON sta.id = c.state_id WHERE UPPER(c.name) LIKE UPPER('%' || :name || '%' ) ORDER BY sta.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchByName(@Param("name") String name);

    @Query(value = "SELECT * FROM city C INNER JOIN State sta ON sta.id = c.state_id WHERE UPPER(c.no_accents) LIKE UPPER('%' || :noAccents || '%' ) ORDER BY sta.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchByNoAccents(@Param("noAccents") String noAccents);

    @Query(value = "SELECT * FROM city C INNER JOIN State sta ON sta.id = c.state_id WHERE UPPER(c.alternative_names) LIKE UPPER('%' || :alternativeNames || '%' ) ORDER BY sta.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchByAlternativeNames(@Param("alternativeNames") String alternativeNames);

    @Query(value = "SELECT * FROM city C INNER JOIN State sta ON sta.id = c.state_id WHERE UPPER(sta.name) LIKE UPPER('%' || :stateName || '%' ) ORDER BY sta.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchStateByName(@Param("stateName") String stateName);

    @Query(value = "SELECT * FROM city C INNER JOIN mesoregion mr ON mr.id = c.mesoregions_id WHERE UPPER(mr.name) LIKE UPPER('%' || :mesoregionName || '%' ) ORDER BY MR.name, C.no_accents", nativeQuery = true)
    List<City> fetchMesoregionsByName(@Param("mesoregionName") String mesoregionName);

    @Query(value = "SELECT * FROM city C INNER JOIN microregion mr ON mr.id = c.microregions_id WHERE UPPER(mr.name) LIKE UPPER('%' || :microregionName || '%' ) ORDER BY MR.name, C.no_accents", nativeQuery = true)
    List<City> fetchMicroregionsByName(@Param("microregionName") String microregionName);

    // COUNTS
    List countByStates();

    List countByName();

    List countByNoAccents();

    List countByAlternativeNames();

    List countByMicroregions();

    List countByMesoregions();

}

//            SELECT CONCAT(A.name, '/', UFA.name) AS from_city, CONCAT(B.name, '/', sta2.name) AS to_city, 
//               111.111 *
//                DEGREES(ACOS(LEAST(COS(RADIANS(a.lat))
//                     * COS(RADIANS(b.lat))
//                     * COS(RADIANS(a.lon- b.lon))
//                     + SIN(RADIANS(a.lat))
//                     * SIN(RADIANS(b.lat)), 1.0))) AS distance_in_km
//              FROM city AS a
//              INNER JOIN Uf AS ufa ON ufa.id = A.state_id 
//              INNER JOIN city AS b ON a.id <> b.id
//              INNER JOIN Uf AS ufb ON ufb.id = B.state_id 
//             WHERE a.id = (select id from city where location_point in (select max(location_point) as max from city)) AND b.id = (select id from city where location_point in (select min(location_point) as max from city))
