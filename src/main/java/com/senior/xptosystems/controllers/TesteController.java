package com.senior.xptosystems.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/test")
public class TesteController {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<String> receiveData(MultipartFile csv) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(csv.getOriginalFilename());
        return ResponseEntity.ok("Deu certo!");
    }
}
