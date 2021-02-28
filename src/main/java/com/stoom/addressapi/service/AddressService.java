package com.stoom.addressapi.service;

import com.stoom.addressapi.model.dto.filter.AddressFilter;
import com.stoom.addressapi.model.dto.request.AddressRequest;
import com.stoom.addressapi.model.dto.response.AddressResponse;
import com.stoom.addressapi.exception.NotFoundException;
import com.stoom.addressapi.model.Address;
import com.stoom.addressapi.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;
    private final GoogleMapsService googleMapsService;

    public Page<AddressResponse> findAll(final AddressFilter filter, final Pageable pageable) {
        final Example<Address> example = Example.of(filter.toAddress(),
                ExampleMatcher.matchingAll().withStringMatcher(CONTAINING).withIgnoreCase());
        return repository.findAll(example,pageable).map(AddressResponse::new);
    }

    public AddressResponse findById(final Long id) {
        return repository.findById(id)
                .map(AddressResponse::new)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(final Long id) {
        repository.deleteById(id);
    }

    public AddressResponse save(final AddressRequest request) {
        if(request.isCoordenadaVazia()){
            googleMapsService.setCoordinates(request);
        }
        return new AddressResponse(repository.saveAndFlush(new AddressRequest.BuilderAddressFromRequest(request).build()));
    }

    public AddressResponse update(final Long id, final AddressRequest request) {
        final Address found = repository.findById(id).orElseThrow(NotFoundException::new);
        if(request.isCoordenadaVazia()){
            googleMapsService.setCoordinates(request);
        }
        final Address addressToUpdate = new AddressRequest.BuilderAddressFromRequest(request).withId(id).build();
        BeanUtils.copyProperties(addressToUpdate, found);
        return new AddressResponse(repository.saveAndFlush(found));
    }
}
