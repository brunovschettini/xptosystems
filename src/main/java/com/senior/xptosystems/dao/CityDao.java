package com.senior.xptosystems.dao;

import com.senior.xptosystems.model.City;
import com.senior.xptosystems.utils.DB;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CityDao {

    public List<City> find(String column, String query) {
        if (column == null || column.isEmpty()) {
            return new ArrayList();
        }
        if (query == null || query.isEmpty()) {
            return new ArrayList();
        }
        String queryString = "";
        switch (column) {
            case "ibge_id":
                queryString = "SELECT C.* FROM city C WHERE C.ibge_id = " + query + " ORDER BY C.name ASC";
                break;
            case "uf":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id WHERE UPPER(u.name) LIKE UPPER('%" + query + "%') ORDER BY u.name ASC, C.no_accents ASC";
                break;
            case "name":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id WHERE UPPER(C.name) LIKE UPPER('%" + query + "%') ORDER BY u.name ASC, C.no_accents ASC";
                break;
            case "lon":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id WHERE C.lon >= " + query + " ORDER BY C.lon ASC";
                break;
            case "lat":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id WHERE C.lat >= " + query + " ORDER BY C.lan ASC";
                break;
            case "no_accents":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id WHERE UPPER(C.no_accents) LIKE UPPER('%" + query + "%') ORDER BY u.name ASC, C.no_accents ASC";
                break;
            case "alternative_names":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id WHERE UPPER(C.alternative_names) LIKE UPPER('%" + query + "%') ORDER BY U.name ASC, C.alternative_names ASC";
                break;
            case "microregion":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id INNER JOIN microregion M ON M.id = C.microregions_id WHERE UPPER(M.name) LIKE UPPER('%" + query + "%') ORDER BY M.name ASC";
                break;
            case "mesoregion":
                queryString = "SELECT C.* FROM city C INNER JOIN Uf u ON u.id = C.uf_id INNER JOIN mesoregion M ON M.id = C.mesoregions_id WHERE UPPER(M.name) LIKE UPPER('%" + query + "%') ORDER BY M.name ASC";
                break;
            default:
                break;
        }
        try {
            DB db = new DB();
            EntityManager em = db.getEntityManager();
            Query q = em.createNativeQuery(queryString, City.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public Integer count(String column) {
        if (column == null || column.isEmpty()) {
            return 0;
        }
        String queryString = "";
        switch (column) {
            case "uf":
                queryString = "SELECT count(*) FROM city C GROUP BY C.uf_id ";
                break;
            case "name":
                queryString = "SELECT count(*) FROM city C GROUP BY C.name ";
                break;
            case "no_accents":
                queryString = "SELECT count(*) FROM city C GROUP BY ORDER BY C.no_accents ";
                break;
            case "alternative_names":
                queryString = "SELECT count(*) FROM city C GROUP BY C.alternative_names ";
                break;
            case "microregion":
                queryString = "SELECT count(*) FROM city C GROUP BY C.microregions_id ";
                break;
            case "mesoregion":
                queryString = "SELECT count(*) FROM city C GROUP BY C.mesoregions_id ";
                break;
            default:
                break;
        }
        try {
            DB db = new DB();
            EntityManager em = db.getEntityManager();
            Query q = em.createNativeQuery(queryString);
            List list = q.getResultList();
            return list.size();
        } catch (Exception e) {
            return 0;
        }
    }

}
