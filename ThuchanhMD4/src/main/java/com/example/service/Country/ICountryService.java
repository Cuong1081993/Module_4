package com.example.service.Country;

import com.example.model.Country;
import com.example.model.dto.CountryDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface ICountryService extends IGeneralService<Country> {
    List<CountryDTO> findAllCountryDTO();
}
