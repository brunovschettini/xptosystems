package com.senior.xptosystems.controllers;

import com.senior.xptosystems.model.State;
import com.senior.xptosystems.repositories.StateRepository;
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
@RequestMapping(path = "/state")
public class StateRestController {

    @Autowired
    StateRepository stateRepository;

    /**
     * Studies http://localhost/api/state/?number=0&size=10&sort=name
     *
     * @param pageable
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<State> states = stateRepository.findAll(pageable);
        if (states.isEmpty()) {
            return new ResponseEntity<>(states, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        State state = stateRepository.findById(id).get();
        if (state == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

}
