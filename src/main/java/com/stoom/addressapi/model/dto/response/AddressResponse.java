package com.stoom.addressapi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stoom.addressapi.model.Address;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Address Response")
public class AddressResponse {

    private final Address address;

    @ApiModelProperty(value = "Address ID")
    public Long getId() {
        return address.getId();
    }

    @ApiModelProperty(value = "Street Name")
    public String getStreetName() {
        return address.getStreetName();
    }

    @ApiModelProperty(value = "Address number")
    public String getNumber() {
        return address.getNumber();
    }

    @ApiModelProperty(value = "Address Complement")
    public String getComplement() {
        return address.getComplement();
    }

    @ApiModelProperty(value = "Neighbourhood")
    public String getNeighbourhood() {
        return address.getNeighbourhood();
    }

    @ApiModelProperty(value = "City")
    public String getCity() {
        return address.getCity();
    }

    @ApiModelProperty(value = "State")
    public String getState() {
        return address.getState();
    }

    @ApiModelProperty(value = "Country")
    public String getCountry() {
        return address.getCountry();
    }

    @ApiModelProperty(value = "Zip Code")
    public String getZipcode() {
        return address.getZipcode();
    }

    @ApiModelProperty(value = "Latitude")
    public String getLatitude() {
        return address.getLatitude();
    }

    @ApiModelProperty(value = "Longitude")
    public String getLongitude() {
        return address.getLongitude();
    }


}
