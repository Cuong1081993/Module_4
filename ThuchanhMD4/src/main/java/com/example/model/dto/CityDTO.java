package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CityDTO {

    private String id;
    private String cityName;
    private String area;
    private String population;
    private String gdp;
    private String description;

    private CountryDTO country;
}
