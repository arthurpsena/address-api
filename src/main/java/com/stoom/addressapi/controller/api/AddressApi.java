package com.stoom.addressapi.controller.api;

import com.stoom.addressapi.config.swagger.ApiPageable;
import com.stoom.addressapi.model.dto.filter.AddressFilter;
import com.stoom.addressapi.model.dto.request.AddressRequest;
import com.stoom.addressapi.model.dto.response.AddressResponse;
import com.stoom.addressapi.model.dto.response.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "AddressApi")
@SwaggerDefinition(tags = { @Tag(name = "AddressApi", description = "Endpoints related to Address Api") })
public interface AddressApi {

    @ApiOperation("Find an address")
    @ApiResponses({ @ApiResponse(code = 200, message = "Address found"),
            @ApiResponse(code = 404, message = "No element was found"),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ErrorResponse.class) })
    ResponseEntity<AddressResponse> findById(@PathVariable("id") final Long id);

    @ApiOperation("Find all addresses")
    @ApiResponses({ @ApiResponse(code = 200, message = "Addresses listed"),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ErrorResponse.class) })
    @ApiPageable
    Page<AddressResponse> findAll(final AddressFilter filter, @PageableDefault final Pageable pageable);

    @ApiOperation("Create an address")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Address created with success", response = AddressResponse.class),
            @ApiResponse(code = 400, message = "Invalid information was sent in payload", response = ErrorResponse.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ErrorResponse.class) })
    ResponseEntity<AddressResponse> save(@Valid @RequestBody final AddressRequest request);

    @ApiOperation("Delete an address")
    @ApiResponses(value = { @ApiResponse(code = 202, message = "Address disabled with success", response = void.class),
            @ApiResponse(code = 404, message = "Address not found", response = ErrorResponse.class) })
    ResponseEntity<Void> delete(@PathVariable("id") final Long id);

    @ApiOperation("Update an address")
    @ApiResponses({ @ApiResponse(code = 202, message = "Address updated with success"),
            @ApiResponse(code = 404, message = "No element was found"),
            @ApiResponse(code = 400, message = "Invalid information was sent in payload", response = ErrorResponse.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ErrorResponse.class) })
    ResponseEntity<Void> update(@PathVariable("id") final Long id, @Valid @RequestBody final AddressRequest request);

}
