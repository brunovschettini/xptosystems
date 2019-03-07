package com.senior.xptosystems.xptosystems.repositories;

import com.senior.xptosystems.xptosystems.model.City;
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
            + "    uf_id, count(*) city_count\n"
            + "    FROM city\n"
            + "    GROUP BY uf_id\n"
            + "    ORDER BY count(*) ASC \n"
            + "    LIMIT 1\n"
            + ") \n"
            + "UNION ALL\n"
            + "(\n"
            + "    SELECT \n"
            + "    uf_id, count(*) city_count\n"
            + "    FROM city\n"
            + "    GROUP BY uf_id\n"
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

    @Query(value = "?1", nativeQuery = true)
    Integer total(String queryString);

//      public List<Cities> capitals() {
//        try {
//            Query query = getEntityManager().createQuery("SELECT C FROM Cities AS C WHERE C.capital = true ORDER BY C.name");
//            return query.getResultList();
//        } catch (Exception e) {
//            return new ArrayList<>();
//        }
//    }
//
//    public Cities exists(Cities c) {
//        try {
//            Query query = getEntityManager().createQuery("SELECT C FROM Cities AS C WHERE UPPER(C.name) = :name AND UPPER(C.uf) = :uf ORDER BY C.name");
//            query.setParameter("name", c.getName());
//            query.setParameter("uf", c.getUf());
//            return (Cities) query.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public Cities findByIBGE(Long ibge_id) {
//        try {
//            Query query = getEntityManager().createQuery("SELECT C FROM Cities AS C WHERE C.ibge_id = :ibge_id");
//            query.setParameter("ibge_id", ibge_id);
//            return (Cities) query.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List min_max_cities_by_state() {
//        try {
//            String queryString = ""
//                    + "(\n"
//                    + "    SELECT \n"
//                    + "    uf, count(*) city_count\n"
//                    + "    FROM cities\n"
//                    + "    GROUP BY uf\n"
//                    + "    ORDER BY count(*) ASC \n"
//                    + "    LIMIT 1\n"
//                    + ") \n"
//                    + "UNION ALL\n"
//                    + "(\n"
//                    + "    SELECT \n"
//                    + "    uf, count(*) city_count\n"
//                    + "    FROM cities\n"
//                    + "    GROUP BY uf\n"
//                    + "    ORDER BY count(*) DESC \n"
//                    + "    LIMIT 1\n"
//                    + ") ";
//            Query query = getEntityManager().createNativeQuery(queryString);
//
//            List list = query.getResultList();
//            return list;
//        } catch (Exception e) {
//        }
//
//        return null;
//    }
//
//    public Integer count_by_state(String uf) {
//        try {
//            String queryString = ""
//                    + "SELECT count(*) \n"
//                    + "FROM cities C\n"
//                    + "WHERE UPPER(C.uf) = UPPER('" + uf + "')";
//            Query query = getEntityManager().createNativeQuery(queryString);
//            List result = query.getResultList();
//            Object o = result.get(0);
//            String str = o + "";
//            return Integer.parseInt(str);
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//    public Cities find_by_ibge_id(String code) {
//        try {
//            Query query = getEntityManager().createQuery("SELECT C FROM Cities C WHERE C.ibge_id = :code");
//            query.setParameter("code", Long.parseLong(code));
//            return (Cities) query.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<Cities> find_by_uf(String uf) {
//        try {
//            Query query = getEntityManager().createQuery("SELECT C FROM Cities C WHERE UPPER(C.uf) = :uf ORDER BY C.name ASC");
//            query.setParameter("uf", uf.toUpperCase());
//            return query.getResultList();
//        } catch (Exception e) {
//            return new ArrayList();
//        }
//    }
//
//    public List<Cities> find(String column, String query) {
//        if (column == null || column.isEmpty()) {
//            return new ArrayList();
//        }
//        if (query == null || query.isEmpty()) {
//            return new ArrayList();
//        }
//        String queryString = "";
//        switch (column) {
//            case "ibge_id":
//                queryString = "SELECT C.* FROM cities C WHERE C.ibge_id = " + query + " ORDER BY C.name ASC";
//                break;
//            case "uf":
//                queryString = "SELECT C.* FROM cities C WHERE C.uf = '" + query + "' ORDER BY C.uf, C.name ASC";
//                break;
//            case "name":
//                queryString = "SELECT C.* FROM cities C WHERE C.name LIKE '%" + query + "%' ORDER BY C.uf, C.name ASC";
//                break;
//            case "lon":
//                queryString = "SELECT C.* FROM cities C WHERE C.lon = " + query + " ORDER BY C.name ASC";
//                break;
//            case "lat":
//                queryString = "SELECT C.* FROM cities C WHERE C.lat = " + query + " ORDER BY C.name ASC";
//                break;
//            case "no_accents":
//                queryString = "SELECT C.* FROM cities C WHERE C.no_accents LIKE '%" + query + "%' ORDER BY C.uf, C.no_accents ASC";
//                break;
//            case "alternative_names":
//                queryString = "SELECT C.* FROM cities C WHERE C.alternative_names LIKE '%" + query + "%' ORDER BY C.uf, C.alternative_names ASC";
//                break;
//            case "micro_region":
//                queryString = "SELECT C.* FROM cities C WHERE C.micro_region LIKE '%" + query + "%' ORDER BY C.uf, C.micro_region ASC";
//                break;
//            case "meso_region":
//                queryString = "SELECT C.* FROM cities C WHERE C.meso_region LIKE '%" + query + "%' ORDER BY C.uf, C.meso_region ASC";
//                break;
//            default:
//                break;
//        }
//        try {
//            Query q = getEntityManager().createNativeQuery(queryString, Cities.class);
//            return q.getResultList();
//        } catch (Exception e) {
//            return new ArrayList();
//        }
//    }
//
//    public Integer total() {
//        try {
//            String queryString = ""
//                    + "SELECT count(*) \n"
//                    + "FROM cities \n";
//            Query query = getEntityManager().createNativeQuery(queryString);
//            List result = query.getResultList();
//            Object o = result.get(0);
//            String str = o + "";
//            return Integer.parseInt(str);
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//    public Integer total(String column) {
//        if (column == null || column.isEmpty()) {
//            return 0;
//        }
//        String queryString = "";
//        switch (column) {
//            case "ibge_id":
//                queryString = "SELECT C.* FROM cities C WHERE C.ibge_id IS NOT NULL ORDER BY C.name ASC";
//                break;
//            case "uf":
//                queryString = "SELECT C.* FROM cities C WHERE C.uf <> '' ORDER BY C.uf, C.name ASC";
//                break;
//            case "name":
//                queryString = "SELECT C.* FROM cities C WHERE C.name <> '' ORDER BY C.uf, C.name ASC";
//                break;
//            case "lon":
//                queryString = "SELECT C.* FROM cities C WHERE C.lon IS NOT NULL null ORDER BY C.name ASC";
//                break;
//            case "lat":
//                queryString = "SELECT C.* FROM cities C WHERE C.lat IS NOT NULL  ORDER BY C.name ASC";
//                break;
//            case "no_accents":
//                queryString = "SELECT C.* FROM cities C WHERE C.no_accents <> '' ORDER BY C.uf, C.no_accents ASC";
//                break;
//            case "alternative_names":
//                queryString = "SELECT C.* FROM cities C WHERE C.alternative_names <> '' ORDER BY C.uf, C.alternative_names ASC";
//                break;
//            case "micro_region":
//                queryString = "SELECT C.* FROM cities C WHERE C.micro_region <> '' ORDER BY C.uf, C.micro_region ASC";
//                break;
//            case "meso_region":
//                queryString = "SELECT C.* FROM cities C WHERE C.meso_region <> '' ORDER BY C.uf, C.meso_region ASC";
//                break;
//            case "capital":
//                queryString = "SELECT C.* FROM cities C WHERE C.capital = 1 ORDER BY C.uf, C.meso_region ASC";
//                break;
//            default:
//                break;
//        }
//        try {
//            Query q = getEntityManager().createNativeQuery(queryString, Cities.class);
//            return q.getResultList().size();
//        } catch (Exception e) {
//            return 0;
//        }
//    }
}
