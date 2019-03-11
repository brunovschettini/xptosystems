package com.senior.xptosystems.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusRestController {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public synchronized ResponseEntity<?> active() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
