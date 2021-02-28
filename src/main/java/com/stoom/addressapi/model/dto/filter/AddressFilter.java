package com.stoom.addressapi.model.dto.filter;

import com.stoom.addressapi.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressFilter {

    private String streetName;
    private String number;
    private String neighbourhood;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    public Address toAddress(){
        return Address.builder()
                .neighbourhood(neighbourhood)
                .city(city)
                .country(country)
                .streetName(streetName)
                .state(state)
                .zipcode(zipcode)
                .number(number)
                .build();
    }

}
