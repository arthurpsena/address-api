package com.stoom.addressapi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stoom.addressapi.model.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {

    @NotBlank
    @ApiModelProperty(value = "Street Name")
    private String streetName;
    @NotBlank
    @ApiModelProperty(value = "Address number")
    private String number;
    @ApiModelProperty(value = "Address Complement")
    private String complement;
    @NotBlank
    @ApiModelProperty(value = "Neighbourhood")
    private String neighbourhood;
    @NotBlank
    @ApiModelProperty(value = "City")
    private String city;
    @NotBlank
    @ApiModelProperty(value = "State")
    private String state;
    @NotBlank
    @ApiModelProperty(value = "Country")
    private String country;
    @NotBlank
    @ApiModelProperty(value = "Zip Code")
    private String zipcode;
    @ApiModelProperty(value = "Latitude")
    private String latitude;
    @ApiModelProperty(value = "Longitude")
    private String longitude;

    @JsonIgnore
    public boolean isCoordenadaVazia() {
        return isBlank(latitude) || isBlank(longitude);
    }

    @JsonIgnore
    public String getAddress() {
        return String.format("%s,%s,%s,%s,%s,%s,%s", streetName, number, neighbourhood, zipcode, city, state, country);
    }

    public static class BuilderAddressFromRequest {

        private Address address;

        public BuilderAddressFromRequest(final AddressRequest addressRequest) {
            address = Address.builder()
                    .state(addressRequest.getState())
                    .zipcode(addressRequest.getZipcode())
                    .streetName(addressRequest.getStreetName())
                    .city(addressRequest.getCity())
                    .neighbourhood(addressRequest.getNeighbourhood())
                    .number(addressRequest.getNumber())
                    .country(addressRequest.getCountry())
                    .complement(addressRequest.getComplement())
                    .latitude(addressRequest.getLatitude())
                    .longitude(addressRequest.getLongitude())
                    .build();
        }

        public BuilderAddressFromRequest withId(final Long id) {
            address.setId(id);
            return this;
        }

        public Address build() {
            return address;
        }

    }

}
