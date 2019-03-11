package com.senior.xptosystems.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.senior.xptosystems.utils.StringsHelper;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        List<City> cities = cityComponent.uploadCSV(csv, true);
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
        if (rows.isEmpty()) {
            return new ResponseEntity<>(rows, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rows, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/total", produces = "application/json")
    public ResponseEntity<?> total() {
        Integer total = cityRepository.total();
        Map<Object, Object> response = new LinkedHashMap();
        response.put("total", total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/count/column/{column}", produces = "application/json")
    public ResponseEntity<?> statsCountByColumnFilter(@PathVariable("column") String column) {
        Map<Object, Object> response = new LinkedHashMap();
        Integer total = new CityDao().count(column);
        response.put("column", column);
        response.put("total", total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/column/{column}/query/{query}", produces = "application/json")
    public ResponseEntity<?> findByColumn(@PathVariable("column") String column, @PathVariable("query") String query) {
        List<City> cities = new CityDao().find(column, query);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/uf/{uf}", produces = "application/json")
    public ResponseEntity<?> findByColumn(@PathVariable("uf") String uf) {
        Uf ufAux = ufRepository.findByNameIgnoreCase(uf);
        if (ufAux == null) {
            return new ResponseEntity<>("uf not found!", HttpStatus.BAD_REQUEST);
        }
        List<City> cities = cityRepository.findByUf_id(ufAux.getId());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/count/cities/uf/{uf}", produces = "application/json")
    public ResponseEntity<?> statsCountCitiesByUf(@PathVariable("uf") String uf_name) throws IOException {
        Uf uf = ufRepository.findByNameIgnoreCase(uf_name);
        if (uf == null) {
            return new ResponseEntity<>("uf not found!", HttpStatus.BAD_REQUEST);
        }
        Integer count = cityRepository.findCountByUf(uf.getId());
        Map<Object, Object> response = new LinkedHashMap();
        response.put("uf", uf.getName());
        response.put("total", count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/ibge_id/{ibge_id}", produces = "application/json")
    public ResponseEntity<?> findByIbgeId(@PathVariable("ibge_id") Long ibge_id) {
        City city = cityRepository.findByIbge_id(ibge_id);
        if (city == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/id/{id}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        City city = cityRepository.getOne(id);
        if (city == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> store(@RequestBody City city) {
        if (city.getId() != null) {
            return new ResponseEntity<>("exists city! use PUT to update!", HttpStatus.BAD_REQUEST);
        }
        if (city.getName().isEmpty()) {
            return new ResponseEntity<>("empty name!", HttpStatus.BAD_REQUEST);
        }
        if (city.getIbge_id() == null || city.getIbge_id() == 0) {
            return new ResponseEntity<>("empty ibge id!", HttpStatus.BAD_REQUEST);
        }
        City c = cityRepository.findByIbge_id(city.getIbge_id());
        if (c != null) {
            return new ResponseEntity<>("exists ibge_id city!", HttpStatus.BAD_REQUEST);
        }
        if (city.getUf() == null || city.getUf().getId() == null) {
            if ((city.getUfName() == null || city.getUfName().isEmpty())) {
                return new ResponseEntity<>("empty uf!", HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>("empty mesoregion aux!", HttpStatus.BAD_REQUEST);
            }
            Mesoregion mesoregion = mesoregionRepository.findByNameIgnoreCase(city.getMesoregionName());
            if (mesoregion == null) {
                mesoregion = new Mesoregion();
                mesoregion.setName(city.getMesoregionName());
                mesoregionRepository.save(mesoregion);
            }
            city.setMesoregions(mesoregion);
        }
        if (city.getMicroregions() == null || city.getMicroregions().getId() == null) {
            if (city.getMicroregionName() == null || city.getMicroregionName().isEmpty()) {
                return new ResponseEntity<>("empty microregion name!", HttpStatus.BAD_REQUEST);
            }
            Microregion microregion = microregionRepository.findByNameIgnoreCase(city.getMicroregionName());
            if (microregion == null) {
                microregion = new Microregion();
                microregion.setName(city.getMicroregionName());
                microregionRepository.save(microregion);
            }
            city.setMicroregions(microregion);
        }
        city.setNoAccents(StringsHelper.unaccent(city.getName()));
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody City city) {

        if (city.getId() == null) {
            return new ResponseEntity<>("new register! use POST to store/save!", HttpStatus.BAD_REQUEST);
        }
        if (city.getName().isEmpty()) {
            return new ResponseEntity<>("empty name!", HttpStatus.BAD_REQUEST);
        }
        if (city.getUfName().isEmpty()) {
            return new ResponseEntity<>("empty uf!", HttpStatus.BAD_REQUEST);
        }
        if (city.getUf() == null || city.getUf().getId() == null) {
            if ((city.getUfName() == null || city.getUfName().isEmpty())) {
                return new ResponseEntity<>("empty uf!", HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>("empty mesoregion aux!", HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>("empty microregion name!", HttpStatus.BAD_REQUEST);
            }
            Microregion microregion = microregionRepository.findByNameIgnoreCase(city.getMicroregionName());
            if (microregion == null) {
                microregion = new Microregion();
                microregion.setName(city.getUfName());
                microregionRepository.save(microregion);
            }
            city.setMicroregions(microregion);
        }
        city.setNoAccents(StringsHelper.unaccent(city.getName()));
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "/{ibge_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByIbgeId(@PathVariable("ibge_id") Long ibge_id) {
        City city = cityRepository.findByIbge_id(ibge_id);
        if (city == null || city.getId() == null) {
            return new ResponseEntity<>("empty city!", HttpStatus.BAD_REQUEST);
        }
        try {
            cityRepository.delete(city);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
