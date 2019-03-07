package com.senior.xptosystems.xptosystems.services;

import com.senior.xptosystems.xptosystems.model.City;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ICityComponent {

    List<City> readCSV();

    List<City> uploadCSV(MultipartFile csv, Boolean replace);

    List<City> storeAll(List<City> cities);

    City store(City city);
}
