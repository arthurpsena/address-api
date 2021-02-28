package com.stoom.addressapi.controller;

import com.stoom.addressapi.controller.api.AddressApi;
import com.stoom.addressapi.model.constants.Mappings;
import com.stoom.addressapi.model.dto.filter.AddressFilter;
import com.stoom.addressapi.model.dto.request.AddressRequest;
import com.stoom.addressapi.model.dto.response.AddressResponse;
import com.stoom.addressapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.PATH_ADDRESS)
public class AddressController implements AddressApi {

    private final AddressService service;

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public Page<AddressResponse> findAll(final AddressFilter filter, @PageableDefault final Pageable pageable) {
        return service.findAll(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<AddressResponse> save(@Valid @RequestBody final AddressRequest request) {
        return ResponseEntity.status(CREATED).body(service.save(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        service.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") final Long id, @Valid @RequestBody final AddressRequest request) {
        service.update(id, request);
        return ResponseEntity.status(NO_CONTENT).build();
    }

}
