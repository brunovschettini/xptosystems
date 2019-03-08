package com.senior.xptosystems.controllers;

import com.senior.xptosystems.utils.StringHelper;
import com.senior.xptosystems.dao.CityDao;
import com.senior.xptosystems.model.City;
import com.senior.xptosystems.model.Mesoregion;
import com.senior.xptosystems.model.Microregion;
import com.senior.xptosystems.model.Uf;
import com.senior.xptosystems.repositories.CityRepository;
import com.senior.xptosystems.repositories.MesoregionRepository;
import com.senior.xptosystems.repositories.MicroregionRepository;
import com.senior.xptosystems.repositories.UfRepository;
import com.senior.xptosystems.services.ICityComponent;
import com.senior.xptosystems.utils.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/city")
public class CityRestController {

    @Autowired
    ICityComponent cityComponent;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UfRepository ufRepository;

    @Autowired
    MicroregionRepository microregionRepository;

    @Autowired
    MesoregionRepository mesoregionRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/upload", produces = "application/json")
    public ResponseEntity<?> receiveData(MultipartFile csv) throws IOException {
        if (csv.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<City> cities = new ArrayList();
        cities = cityComponent.uploadCSV(csv, true);
        if (!cities.isEmpty()) {
            cityComponent.storeAll(cities);
            return new ResponseEntity<>(cities, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/capitals", produces = "application/json")
    public ResponseEntity<?> findCapitals() throws IOException {
        List<City> list = cityRepository.findByCapitalOrderByNameAsc();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/min_max_uf", produces = "application/json")
    public ResponseEntity<?> statsUfMinMax() throws IOException {
        List<Object[]> rows = cityRepository.findMinMaxCitiesByUf();
        if (!rows.isEmpty()) {
            String test = rows.get(0)[0].toString();
            String result = "{\"uf_bigger_cities\": " + rows.get(0)[0].toString() + "  ,\"uf_smaller_cities\":" + rows.get(0)[1].toString() + " }";
            return new ResponseEntity<>(rows, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/total/cities", produces = "application/json")
    public ResponseEntity<?> total() {
        Integer total = cityRepository.total();
        if (total == 0) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        String result = "{\"total\": " + total + "}";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/count/column/{column}", produces = "application/json")
    public ResponseEntity<?> statsCountByColumnFilter(@PathVariable("column") String column) {
        if (column == null || column.isEmpty()) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        Integer total = new CityDao().count(column);
        String result = "{\"total\": " + total + "}";
        // return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/column/{column}/query/{query}", produces = "application/json")
    public ResponseEntity<?> findByColumn(@PathVariable("column") String column, @PathVariable("query") String query) {
        if (column == null || column.isEmpty()) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        List<City> cities = new CityDao().find(column, query);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/count/cities/uf/{uf}", produces = "application/json")
    public ResponseEntity<?> statsCountCitiesByUf(@PathVariable("uf") String uf_name) throws IOException {
        if (uf_name == null || uf_name.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Uf uf = ufRepository.findByNameIgnoreCase(uf_name);
        if (uf == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        JSONArray array = new JSONArray();
        Integer count = cityRepository.findCountByUf(uf.getId());
        String json = "{\"uf\":\"" + uf.getName() + "\", \"total\": " + count + "}";
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/ibge_id/{ibge_id}", produces = "application/json")
    public ResponseEntity<?> findByIbgeId(@PathVariable("ibge_id") Long ibge_id) {
        if (ibge_id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        City city = cityRepository.findByIbge_id(ibge_id);
        if (city != null) {
            return new ResponseEntity<>(city, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> store(@RequestBody City city) {
        Result result = new Result();
        if (city.getId() != null) {
            result.setStatus_code(0);
            result.setStatus("exists city! use PUT to update!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty name!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getIbge_id() == null || city.getIbge_id() == 0) {
            result.setStatus_code(0);
            result.setStatus("empty ibge id!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        City c = cityRepository.findByIbge_id(city.getIbge_id());
        if(c != null) {
            result.setStatus_code(0);
            result.setStatus("exists ibge_id city!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);            
        }
        if (city.getUf() == null || city.getUf().getId() == null) {
            if ((city.getUfName() == null || city.getUfName().isEmpty())) {
                result.setStatus_code(0);
                result.setStatus("empty uf!");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            Uf uf = ufRepository.findByNameIgnoreCase(city.getUfName());
            if (uf == null) {
                uf = new Uf();
                uf.setName(city.getUfName());
                ufRepository.save(uf);
            }
            city.setUf(uf);
        }
        if (city.getMesoregions() == null || city.getMesoregions().getId() == null) {
            if (city.getMesoregionName() == null || city.getMesoregionName().isEmpty()) {
                result.setStatus_code(0);
                result.setStatus("empty mesoregion aux!");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            Mesoregion mesoregion = mesoregionRepository.findByNameIgnoreCase(city.getMesoregionName());
            if (mesoregion == null) {
                mesoregion = new Mesoregion();
                mesoregion.setName(city.getUfName());
                mesoregionRepository.save(mesoregion);
            }
            city.setMesoregions(mesoregion);
        }
        if (city.getMicroregions() == null || city.getMicroregions().getId() == null) {
            if (city.getMicroregionName() == null || city.getMicroregionName().isEmpty()) {
                result.setStatus_code(0);
                result.setStatus("empty microregion name!");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            Microregion microregion = microregionRepository.findByNameIgnoreCase(city.getMicroregionName());
            if (microregion == null) {
                microregion = new Microregion();
                microregion.setName(city.getUfName());
                microregionRepository.save(microregion);
            }
            city.setMicroregions(microregion);
        }
        city.setNoAccents(StringHelper.unaccent(city.getName()));
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            result.setStatus_code(0);
            result.setStatus("city e->" + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        result.setStatus("success: city nº " + city.getId() + " registered");
        result.setResult(city);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody City city) {
        Result result = new Result();
        if (city.getId() == null) {
            result.setStatus_code(0);
            result.setStatus("new register! use POST to store/save!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty name!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getUfName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty uf!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getUf() == null || city.getUf().getId() == null) {
            if ((city.getUfName() == null || city.getUfName().isEmpty())) {
                result.setStatus_code(0);
                result.setStatus("empty uf!");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            Uf uf = ufRepository.findByNameIgnoreCase(city.getUfName());
            if (uf == null) {
                uf = new Uf();
                uf.setName(city.getUfName());
                ufRepository.save(uf);
            }
            city.setUf(uf);
        }
        if (city.getMesoregions() == null || city.getMesoregions().getId() == null) {
            if (city.getMesoregionName() == null || city.getMesoregionName().isEmpty()) {
                result.setStatus_code(0);
                result.setStatus("empty mesoregion aux!");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            Mesoregion mesoregion = mesoregionRepository.findByNameIgnoreCase(city.getMesoregionName());
            if (mesoregion == null) {
                mesoregion = new Mesoregion();
                mesoregion.setName(city.getUfName());
                mesoregionRepository.save(mesoregion);
            }
            city.setMesoregions(mesoregion);
        }
        if (city.getMicroregions() == null || city.getMicroregions().getId() == null) {
            if (city.getMicroregionName() == null || city.getMicroregionName().isEmpty()) {
                result.setStatus_code(0);
                result.setStatus("empty microregion name!");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            Microregion microregion = microregionRepository.findByNameIgnoreCase(city.getMicroregionName());
            if (microregion == null) {
                microregion = new Microregion();
                microregion.setName(city.getUfName());
                microregionRepository.save(microregion);
            }
            city.setMicroregions(microregion);
        }
        city.setNoAccents(StringHelper.unaccent(city.getName()));
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            result.setStatus_code(0);
            result.setStatus("city e->" + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        result.setStatus("success: city nº " + city.getId() + " registered");
        result.setResult(city);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Result result = new Result();
        if (id == null) {
            result.setStatus_code(0);
            result.setStatus("empty city id!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        City city = cityRepository.getOne(id);
        if (city == null || city.getId() == null) {
            result.setStatus_code(0);
            result.setStatus("empty city!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            cityRepository.delete(city);
            // em.flush();
        } catch (Exception e) {
            result.setStatus_code(0);
            result.setStatus("city e->" + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        result.setStatus("success: city nº " + city.getId() + " removed");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/ibge_id/{ibge_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByIbgeId(@PathVariable("ibge_id") Long ibge_id) {
        Result result = new Result();
        if (ibge_id == null) {
            result.setStatus_code(0);
            result.setStatus("empty ibge_id id!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        City city = cityRepository.findByIbge_id(ibge_id);
        if (city == null || city.getId() == null) {
            result.setStatus_code(0);
            result.setStatus("empty city!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            cityRepository.delete(city);
            // em.flush();
        } catch (Exception e) {
            result.setStatus_code(0);
            result.setStatus("city e->" + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.setStatus("success: city nº " + city.getIbge_id() + " removed");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
