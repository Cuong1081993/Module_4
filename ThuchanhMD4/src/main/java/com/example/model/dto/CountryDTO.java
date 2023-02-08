package com.example.model.dto;

import com.example.model.Country;
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
public class CountryDTO {
    private String id;

    private String nameCountry;
    public Country toCountry() {
        return new Country()
                .setId(Long.parseLong(id))
                .setNameCountry(nameCountry);
    }

    public CountryDTO(Long id, String nameCountry) {
        this.id = id.toString();
        this.nameCountry = nameCountry;
    }
}
