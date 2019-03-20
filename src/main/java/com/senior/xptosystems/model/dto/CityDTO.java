package com.senior.xptosystems.model.dto;

import com.senior.xptosystems.model.City;

public class CityDTO {

    private Long ibgeId;
    private String name;
    private Boolean capital;
    private Double lon;
    private Double lat;
    private String noAccents;
    private String alternativeNames;
    private String state;
    private String microregion;
    private String mesoregion;

    public City toObject() {
        return new City(ibgeId, state, name, capital, lon, lat, noAccents, alternativeNames, microregion, mesoregion);
    }
}
