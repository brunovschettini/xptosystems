package com.senior.xptosystems.controllers;

import com.senior.xptosystems.model.Microregion;
import com.senior.xptosystems.repositories.MicroregionRepository;
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
@RequestMapping(path = "/microregion")
public class MicroregionRestController {

    @Autowired
    MicroregionRepository microregionRepository;

    /**
     * Studies http://localhost/api/microregions/?number=0&size=10&sort=name
     *
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<Microregion> microregions = microregionRepository.findAll(pageable);
        if (microregions.isEmpty()) {
            return new ResponseEntity<>(microregions, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(microregions, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Microregion microregion = microregionRepository.findById(id).get();
        if (microregion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(microregion, HttpStatus.OK);
    }

}
