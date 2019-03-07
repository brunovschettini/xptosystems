package com.senior.xptosystems.xptosystems.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.senior.xptosystems.xptosystems.config.Resources;
import com.senior.xptosystems.xptosystems.model.City;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/city")
public class CityController {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<?> receiveData(MultipartFile csv) throws IOException {
        File pathFiles = new File(Resources.INSTANCE + "/" + "files" + "/");
        String target = pathFiles + "/cities.csv";
        if (!pathFiles.exists()) {
            pathFiles.mkdirs();
        }
        String fileNameT = null;
        String fileName = pathFiles + "/cities.csv";
        File csvFile = new File(fileName);
        if (csvFile.exists()) {
            csvFile.delete();
        }
        csvFile = new File(fileName);
        fileNameT = csv.getOriginalFilename();
        if (!csv.isEmpty()) {
            try {
                fileName = csv.getOriginalFilename();
                byte[] bytes = csv.getBytes();
                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(csvFile));
                buffStream.write(bytes);
                buffStream.close();
            } catch (Exception e) {
            }
        }
        Path movefrom = FileSystems.getDefault().getPath(csvFile.getAbsolutePath());
        Path moveTo = FileSystems.getDefault().getPath(pathFiles.getAbsolutePath() + "/cities.csv");
        csvFile = new File(pathFiles.getAbsolutePath() + "/cities.csv");
        Files.move(movefrom, moveTo, StandardCopyOption.REPLACE_EXISTING);
        ObjectMapper mapper = new ObjectMapper();
        FileInputStream fis = null;
        List<City> cities = new ArrayList();
        try {
            fis = new FileInputStream(csvFile);
            CSVReader reader = new CSVReader(new InputStreamReader(fis));
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                City c = new City(null, Long.parseLong(nextLine[0]), null,  nextLine[2], Boolean.parseBoolean(nextLine[3]), Double.parseDouble(nextLine[4]), Double.parseDouble(nextLine[5]), nextLine[6], nextLine[7], null, null, null);
                cities.add(c);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CityService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CityService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CityService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
