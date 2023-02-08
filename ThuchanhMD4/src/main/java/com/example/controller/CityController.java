package com.example.controller;

import com.example.model.City;
import com.example.model.Country;
import com.example.model.dto.CityDTO;
import com.example.model.dto.CountryDTO;
import com.example.service.City.ICityService;
import com.example.service.Country.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/city")
public class CityController {
    @Autowired
    ICityService cityService;
    @Autowired
    ICountryService countryService;

    @GetMapping
    public ModelAndView showListCities() {
        ModelAndView modelAndView = new ModelAndView();
        List<City> cities = cityService.findAll();
        modelAndView.addObject("cities", cities);
        modelAndView.setViewName("/city/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        List<CountryDTO> countryDTOS = countryService.findAllCountryDTO();
        modelAndView.addObject("countries", countryDTOS);
        City city = new City();
        modelAndView.addObject("city", city);
        modelAndView.setViewName("/city/create");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showFormEditCity(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/city/edit");
        List<CountryDTO> countryDTOS = countryService.findAllCountryDTO();
        modelAndView.addObject("countries", countryDTOS);
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            modelAndView.addObject("error", "The City doesn't exits");
            return modelAndView;
        }
        City newCity = city.get();
        modelAndView.addObject("city", newCity);
        modelAndView.setViewName("/city/edit");
        return modelAndView;
    }

    @GetMapping("/deleted/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        cityService.remove(id);
        List<City> cities =  cityService.findAll();
        modelAndView.addObject("cities", cities);
        modelAndView.setViewName("/city/list");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createNewCity(@Validated @ModelAttribute City city, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<City> cities = cityService.findAll();
        modelAndView.addObject("cities", cities);

        if (bindingResult.hasFieldErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> list = bindingResult.getAllErrors();

            for (ObjectError objectError : list) {
                errors.add(objectError.getDefaultMessage());
            }

            List<Country> countries = countryService.findAll();
            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", city);
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("/city/create");

            return modelAndView;
        }

        cityService.save(city);
        modelAndView.setViewName("redirect:/city");

        return modelAndView;
    }

    @PostMapping("edit/{id}")
    public ModelAndView ediCity(@PathVariable Long id, @Validated @ModelAttribute City city, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<City> cities = cityService.findAll();
        modelAndView.addObject("cities", cities);

        if (bindingResult.hasFieldErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> list = bindingResult.getAllErrors();

            for (ObjectError objectError : list) {
                errors.add(objectError.getDefaultMessage());
            }

            modelAndView.addObject("city", city);
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("/city/edit");
            return modelAndView;
        }

        cityService.save(city);
        modelAndView.setViewName("redirect:/city");
        modelAndView.addObject("cities", cities);

        return modelAndView;
    }

    @PostMapping("/deleted/{id}")
    public ModelAndView deletedCity(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        cityService.remove(id);
        List<City> cities = cityService.findAll();
        modelAndView.addObject("cities", cities);
        modelAndView.setViewName("redirect:/city");
        return modelAndView;
    }
}