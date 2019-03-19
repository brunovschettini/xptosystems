package com.senior.xptosystems.controllers;

import com.senior.xptosystems.utils.StringsHelper;
import com.senior.xptosystems.dao.CityDao;
import com.senior.xptosystems.model.City;
import com.senior.xptosystems.model.Mesoregion;
import com.senior.xptosystems.model.Microregion;
import com.senior.xptosystems.model.State;
import com.senior.xptosystems.repositories.CityRepository;
import com.senior.xptosystems.repositories.MesoregionRepository;
import com.senior.xptosystems.repositories.MicroregionRepository;
import com.senior.xptosystems.repositories.StateRepository;
import com.senior.xptosystems.services.ICityComponent;
import com.senior.xptosystems.utils.Error;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
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
    StateRepository stateRepository;

    @Autowired
    MicroregionRepository microregionRepository;

    @Autowired
    MesoregionRepository mesoregionRepository;

    /**
     * Studies http://localhost/api/city/?number=0&size=10&sort=name
     *
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<City> cities = cityRepository.findAll(pageable);
        if (cities.isEmpty()) {
            return new ResponseEntity<>(cities, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/readCsv", produces = "application/json")
    public ResponseEntity<?> readCsv(MultipartFile csv) throws IOException {
        if (csv.isEmpty()) {
            return new ResponseEntity<>(Error.NOT_FOUND("CSV is empty"), HttpStatus.NOT_FOUND);
        }
        List<City> cities = cityComponent.uploadCSV(csv, true);
        if (cities.isEmpty()) {
            return new ResponseEntity<>(cities, HttpStatus.NOT_FOUND);
        }
        cityComponent.storeAll(cities);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/capital", produces = "application/json")
    public ResponseEntity<?> capital() throws IOException {
        List<City> list = cityRepository.capital();
        if (list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/statesBiggerAndSmallerNumberOfCities", produces = "application/json")
    public ResponseEntity<?> statesBiggerAndSmallerNumberOfCities() throws IOException {
        List<Object[]> rows = cityRepository.statesBiggerAndSmallerNumberOfCities();
        if (rows.isEmpty()) {
            return new ResponseEntity<>(rows, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rows, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countAll", produces = "application/json")
    public ResponseEntity<?> countAll() {
        // List<City> listx = cityRepository.findByMicroregion("a");
        Integer total = cityRepository.countAll();
        Map<Object, Object> response = new LinkedHashMap();
        response.put("total", total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countByColumn/{column}", produces = "application/json")
    public ResponseEntity<?> countByColumn(@PathVariable("column") String column) {

        Map<Object, Object> response = new LinkedHashMap();
        // Integer total = new CityDao().count(column);
        Integer total = 0;
        switch (column) {
            case "uf":
            case "ufs":
            case "ufName":
            case "state":
            case "states":
            case "stateName":
                total = cityRepository.countByStates().size();
                break;
            case "name":
            case "names":
                total = cityRepository.countByName().size();
                break;
            case "noAccent":
            case "noAccents":
            case "no_accent":
            case "no_accents":
                total = cityRepository.countByNoAccents().size();
                break;
            case "alternativeName":
            case "alternativeNames":
            case "alternative_name":
            case "alternative_names":
                total = cityRepository.countByAlternativeNames().size();
                ;
                break;
            case "microregion":
            case "microregions":
            case "microregionName":
            case "microregionsName":
                total = cityRepository.countByMicrorgions().size();
                break;
            case "mesoregion":
            case "mesoregions":
            case "mesoregionName":
            case "mesoregionsName":
                total = cityRepository.countByMesoregions().size();
                break;
            default:
                break;
        }

        response.put("column", column);
        response.put("total", total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByColumn/{column}/{filter}", produces = "application/json")
    public ResponseEntity<?> findByColumn(@PathVariable("column") String column, @PathVariable("filter") String filter) {
        List<City> cities = new ArrayList();
        switch (column) {
            case "ibge_id":
            case "ibgeId":
                try {
                    Long ibgeId = Long.parseLong(filter);
                    City c = cityRepository.findByIbgeId(ibgeId);
                    if (c != null) {
                        cities.add(c);
                    }
                } catch (NumberFormatException nfe) {
                    return new ResponseEntity<>(Error.BAD_REQUEST(nfe.getMessage()), HttpStatus.BAD_REQUEST);
                }
                break;
            case "uf":
            case "ufs":
            case "ufName":
            case "state":
            case "states":
                cities = cityRepository.fetchStateByName(filter);
                break;
            case "name":
            case "names":
                cities = cityRepository.fetchByName(filter);
                break;
            case "lon":
                cities = new CityDao().find(column, filter);
                break;
            case "lat":
                cities = new CityDao().find(column, filter);
                break;
            case "noAccent":
            case "noAccents":
            case "no_accent":
            case "no_accents":
                cities = cityRepository.fetchByNoAccents(filter);
                break;
            case "alternativeName":
            case "alternativeNames":
            case "alternative_name":
            case "alternative_names":
                cities = cityRepository.fetchByAlternativeNames(filter);
                break;
            case "microregion":
            case "microregions":
            case "microregionName":
            case "microregionsName":
                cities = cityRepository.fetchMicroregionsByName(filter);
                break;
            case "mesoregion":
            case "mesoregions":
            case "mesoregionName":
            case "mesoregionsName":
                cities = cityRepository.fetchMesoregionsByName(filter);
                break;
            default:
                break;
        }
        if (cities.isEmpty()) {
            return new ResponseEntity<>(cities, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByState/{state}", produces = "application/json")
    public ResponseEntity<?> findByState(@PathVariable("state") String state) {
        State stateAux = stateRepository.findByNameIgnoreCase(state);
        if (stateAux == null) {
            return new ResponseEntity<>(Error.BAD_REQUEST("state not found!"), HttpStatus.BAD_REQUEST);
        }
        List<City> cities = cityRepository.findByState(stateAux.getId());
        if (cities.isEmpty()) {
            return new ResponseEntity<>(cities, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "numberOfCitiesByState/{stateName}", produces = "application/json")
    public ResponseEntity<?> numberOfCitiesByState(@PathVariable("state") String stateName) throws IOException {
        State state = stateRepository.findByNameIgnoreCase(stateName);
        if (state == null) {
            return new ResponseEntity<>(Error.BAD_REQUEST("state not found!"), HttpStatus.BAD_REQUEST);
        }
        Integer count = cityRepository.numberOfCitiesByState(state.getId());
        Map<Object, Object> response = new LinkedHashMap();
        response.put("state", state.getName());
        response.put("total", count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByIbgeId/{ibgeId}", produces = "application/json")
    public ResponseEntity<?> findByIbgeId(@PathVariable("ibgeId") Long ibgeId) {
        City city = cityRepository.findByIbgeId(ibgeId);
        if (city == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findTwoDistanceCities", produces = "application/json")
    public ResponseEntity<?> findTwoDistanceCities() {
        List<Object[]> rows = cityRepository.twoCitiesMoreDistant();
        if (rows.isEmpty()) {
            return new ResponseEntity<>(new ArrayList(), HttpStatus.NOT_FOUND);
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("from", rows.get(0)[0]);
        map.put("to", rows.get(0)[1]);
        map.put("distance", rows.get(0)[2]);
        map.put("factor", "km");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        City city = cityRepository.findById(id).get();
        if (city == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> store(@RequestBody City city) {
        if (city.getId() != null) {
            return new ResponseEntity<>(Error.BAD_REQUEST("exists city! use PUT to update!"), HttpStatus.BAD_REQUEST);
        }
        if (city.getName().isEmpty()) {
            return new ResponseEntity<>(Error.BAD_REQUEST("empty name!"), HttpStatus.BAD_REQUEST);
        }
        if (city.getIbgeId() == null || city.getIbgeId() == 0) {
            return new ResponseEntity<>(Error.BAD_REQUEST("empty ibgeId!"), HttpStatus.BAD_REQUEST);
        }
        City c = cityRepository.findByIbgeId(city.getIbgeId());
        if (c != null) {
            return new ResponseEntity<>(Error.BAD_REQUEST("exists ibgeId city!"), HttpStatus.BAD_REQUEST);
        }
        if (city.getState() == null || city.getState().getId() == null) {
            if ((city.getUfName() == null || city.getUfName().isEmpty())) {
                return new ResponseEntity<>(Error.BAD_REQUEST("empty state/uf!"), HttpStatus.BAD_REQUEST);
            }
            State state = stateRepository.findByNameIgnoreCase(city.getUfName());
            if (state == null) {
                state = new State();
                state.setName(city.getUfName());
                stateRepository.save(state);
            }
            city.setState(state);
        }
        if (city.getMesoregions() == null || city.getMesoregions().getId() == null) {
            if (city.getMesoregionName() == null || city.getMesoregionName().isEmpty()) {
                return new ResponseEntity<>(Error.BAD_REQUEST("empty mesoregion aux!"), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(Error.BAD_REQUEST("empty microregion name!"), HttpStatus.BAD_REQUEST);
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
            city.setLocationPoint(new Point(city.getLat(), city.getLon()));
            cityRepository.save(city);
        } catch (Exception e) {
            return new ResponseEntity<>(Error.INTERNAL_SERVER_ERROR(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody City city) {
        if (city.getId() == null) {
            return new ResponseEntity<>(Error.BAD_REQUEST("new register! use POST to store/save!"), HttpStatus.BAD_REQUEST);
        }
        if (city.getName().isEmpty()) {
            return new ResponseEntity<>(Error.BAD_REQUEST("empty name!"), HttpStatus.BAD_REQUEST);
        }
        if (city.getUfName().isEmpty()) {
            return new ResponseEntity<>(Error.BAD_REQUEST("empty uf name/state!"), HttpStatus.BAD_REQUEST);
        }
        if (city.getState() == null || city.getState().getId() == null) {
            if ((city.getUfName() == null || city.getUfName().isEmpty())) {
                return new ResponseEntity<>(Error.BAD_REQUEST("empty uf name/state!"), HttpStatus.BAD_REQUEST);
            }
            State state = stateRepository.findByNameIgnoreCase(city.getUfName());
            if (state == null) {
                state = new State();
                state.setName(city.getUfName());
                stateRepository.save(state);
            }
            city.setState(state);
        }
        if (city.getMesoregions() == null || city.getMesoregions().getId() == null) {
            if (city.getMesoregionName() == null || city.getMesoregionName().isEmpty()) {
                return new ResponseEntity<>(Error.BAD_REQUEST("empty mesoregion aux!"), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(Error.BAD_REQUEST("empty microregion name!"), HttpStatus.BAD_REQUEST);
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
        city.setLocationPoint(new Point(city.getLat(), city.getLon()));
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            return new ResponseEntity<>(Error.INTERNAL_SERVER_ERROR(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        City city = cityRepository.findById(id).get();
        if (city == null || city.getId() == null) {
            return new ResponseEntity<>(Error.BAD_REQUEST("city not found!"), HttpStatus.NOT_FOUND);
        }
        try {
            cityRepository.delete(city);
        } catch (Exception e) {
            return new ResponseEntity<>(Error.INTERNAL_SERVER_ERROR(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/deleteByIbgeId/{ibgeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByIbgeId(@PathVariable("ibgeId") Long ibgeId) {
        City city = cityRepository.findByIbgeId(ibgeId);
        if (city == null || city.getId() == null) {
            return new ResponseEntity<>(Error.BAD_REQUEST("city not found!"), HttpStatus.NOT_FOUND);
        }
        try {
            cityRepository.delete(city);
        } catch (Exception e) {
            return new ResponseEntity<>(Error.INTERNAL_SERVER_ERROR(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
