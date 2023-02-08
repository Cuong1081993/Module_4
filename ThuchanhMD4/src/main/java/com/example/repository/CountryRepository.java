package com.example.repository;

import com.example.model.Country;
import com.example.model.dto.CountryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("SELECT New com.example.model.dto.CountryDTO(country.id, country.nameCountry) FROM Country AS country")
    List<CountryDTO> findAllCountryDTO();
}
