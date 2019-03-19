package com.senior.xptosystems.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.senior.xptosystems.config.Settings;
import com.senior.xptosystems.model.City;
import com.senior.xptosystems.model.Mesoregion;
import com.senior.xptosystems.model.Microregion; 
import com.senior.xptosystems.model.State;
import com.senior.xptosystems.repositories.CityRepository;
import com.senior.xptosystems.repositories.MesoregionRepository;
import com.senior.xptosystems.repositories.MicroregionRepository; 
import com.senior.xptosystems.repositories.StateRepository;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CityComponent implements ICityComponent {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    Settings settings;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    MicroregionRepository microregionRepository;

    @Autowired
    MesoregionRepository mesoregionRepository;

    @Deprecated
    public List<City> uploadCSV(MultipartFile csv) {
        return null;
    }

    @Override
    public List<City> readCSV() {
        List<City> cities = new ArrayList();
        File csvFile = new File(settings.getResource() + "/" + "files" + "/" + "cities.csv");
        if (!csvFile.exists()) {
            new Throwable("empty csv file");
        }
        ObjectMapper mapper = new ObjectMapper();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(csvFile);
            CSVReader reader = new CSVReader(new InputStreamReader(fis));
            String[] nextLine;
            reader.readNext();
            int i = 0;
            while ((nextLine = reader.readNext()) != null) {
                City c = new City(Long.parseLong(nextLine[0]), nextLine[1], nextLine[2], Boolean.parseBoolean(nextLine[3]), Double.parseDouble(nextLine[4]), Double.parseDouble(nextLine[5]), nextLine[6], nextLine[7], nextLine[8], nextLine[9]);
                cities.add(c);
                i++;
                if (settings.getLimit() != null && settings.getLimit() != 0) {
                    if (i == settings.getLimit()) {
                        break;
                    }
                }
            }
            return cities;
        } catch (FileNotFoundException ex) {
            new Throwable(ex);
        } catch (IOException ex) {
            new Throwable(ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                new Throwable(ex);
            }
        }
        return new ArrayList();
    }

    @Override
    public List<City> uploadCSV(MultipartFile csv, Boolean replace) {
        List<City> cities = new ArrayList();
        File pathFiles = new File(settings.getResource() + "/" + "files" + "/");
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
        if (csv.isEmpty()) {
            new Throwable("Empty csv file");
            return new ArrayList();
        }
        try {
            fileName = csv.getOriginalFilename();
            byte[] bytes = csv.getBytes();
            BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(csvFile));
            buffStream.write(bytes);
            buffStream.close();
            Path movefrom = FileSystems.getDefault().getPath(csvFile.getAbsolutePath());
            Path moveTo = FileSystems.getDefault().getPath(pathFiles.getAbsolutePath() + "/cities.csv");
            csvFile = new File(pathFiles.getAbsolutePath() + "/cities.csv");
            Files.move(movefrom, moveTo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            new Throwable(e);
        }
        return new ArrayList();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<City> storeAll(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            return new ArrayList();
        }
        int count = 1;
        int perMem = 0;
        System.out.println(new Date().toInstant());
        System.out.print("percent completed:   0 %");
        int dot = 0;
        String dotString = "";
        for (City c : cities) {
            if (c.getId() == null) {
                City c1 = cityRepository.findByIbgeId(c.getIbgeId());
                if (c1 == null) {
                    if (c.getState()== null) {
                        State state = stateRepository.findByNameIgnoreCase(c.getUfName());
                        if (state == null) {
                            state = new State();
                            state.setName(c.getUfName());
                            stateRepository.save(state);
                        }
                        c.setState(state);
                    }
                }
                if (c.getMicroregions() == null) {
                    Microregion microregion = this.microregionRepository.findByNameIgnoreCase(c.getMicroregionName());
                    if (microregion == null) {
                        microregion = new Microregion();
                        microregion.setName(c.getMicroregionName());
                        microregionRepository.save(microregion);
                    }
                    c.setMicroregions(microregionRepository.getOne(microregion.getId()));
                }
                if (c.getMesoregions() == null) {
                    Mesoregion mesoregion = this.mesoregionRepository.findByNameIgnoreCase(c.getMesoregionName());
                    if (mesoregion == null) {
                        mesoregion = new Mesoregion();
                        mesoregion.setName(c.getMesoregionName());
                        mesoregionRepository.save(mesoregion);
                    }
                    c.setMesoregions(mesoregionRepository.getOne(mesoregion.getId()));
                }
            }            
            cityRepository.save(c);
            int per = ((count * 100) / cities.size());
            if (perMem != per) {
                perMem = per;
                System.out.printf("\b\b\b\b\b%3d %%" + dotString, (per));
                // System.out.println("progress: " + per + "% of " + 100 + "%");
            }
            dot++;
            if (dot == 4) {
                dot = 0;
                dotString = "";
            } else {
                dotString += ".";
            }
            count++;
        }
        return cities;
    }

    @Override
    public City store(City city) {
        return cityRepository.save(city);
    }

}
