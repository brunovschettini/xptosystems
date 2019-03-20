package com.senior.xptosystems.controllers;

import com.senior.xptosystems.model.Mesoregion;
import com.senior.xptosystems.repositories.MesoregionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/mesoregion")
public class MesoregionRestController {

    @Autowired
    MesoregionRepository mesoregionRepository;

    /**
     * Studies http://localhost/api/mesoregion/?number=0&size=10&sort=name
     *
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<Mesoregion> mesoregions = mesoregionRepository.findAll(pageable);
        if (mesoregions.isEmpty()) {
            return new ResponseEntity<>(mesoregions, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mesoregions, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Mesoregion mesoregion = mesoregionRepository.findById(id).get();
        if (mesoregion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mesoregion, HttpStatus.OK);
    }

}
