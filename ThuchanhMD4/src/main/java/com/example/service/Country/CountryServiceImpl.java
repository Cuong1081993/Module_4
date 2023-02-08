package com.example.service.Country;

import com.example.model.Country;
import com.example.model.dto.CountryDTO;
import com.example.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements ICountryService {

    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<CountryDTO> findAllCountryDTO() {
        return countryRepository.findAllCountryDTO();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public Country getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
