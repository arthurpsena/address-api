package com.stoom.addressapi.service;

import com.stoom.addressapi.exception.CommunicationException;
import com.stoom.addressapi.model.dto.filter.AddressFilter;
import com.stoom.addressapi.model.dto.request.AddressRequest;
import com.stoom.addressapi.model.Address;
import com.stoom.addressapi.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService service;

    @Mock
    private AddressRepository repository;

    @Mock
    private GoogleMapsService googleMapsService;

    private Address address;

    private AddressRequest request;

    @BeforeEach
    public void setup() {

        address = Address.builder()
                .id(1L)
                .city("São Paulo")
                .country("Brasil")
                .neighbourhood("Vila Mariana")
                .number("143")
                .streetName("Abilio Soares")
                .zipcode("06026000")
                .state("SP")
                .build();

        request = AddressRequest
                .builder()
                .city("Osasco")
                .country("Brasil")
                .neighbourhood("Vila Yara")
                .number("154")
                .streetName("Victor Brecheret")
                .zipcode("06026000")
                .state("SP")
                .build();
    }

    @Test
    void deveListarTodosOsEnderecos() {

        when(repository.findAll(any(), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(address)));

        assertThat(service.findAll(AddressFilter.builder()
                        .city("Osasco")
                        .country("Brasil")
                        .neighbourhood("Vila Yara")
                        .number("154")
                        .streetName("Victor Brecheret")
                        .zipcode("06026000")
                        .state("SP").build(),
                PageRequest.of(0, 10)).getTotalElements(), equalTo(1L));
    }

    @Test
    void deveListarNenhumElemento() {

        when(repository.findAll(any(), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThat(service.findAll(new AddressFilter(),
                PageRequest.of(0, 10)).getTotalElements(), equalTo(0L));
    }


    @Test
    void deveAcharUmEndereco() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(address));
        assertThat(service.findById(1L).getId(), equalTo(1L));
    }

    @Test
    void deveLancarUmaExcecaoPorNaoEncontrarEnderecoPorId() {
        when(repository.findById(anyLong())).
                thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1L));
    }

    @Test
    void deveDeletarUmRegistro() {
        service.delete(1L);
        verify(repository).deleteById(anyLong());
    }

    @Test
    void deveInserirUmNovoRegistroComCoordenadasVazias() {
        service.save(request);
        verify(googleMapsService).setCoordinates(any(AddressRequest.class));
        verify(repository).saveAndFlush(any(Address.class));
    }

    @Test
    void deveInserirUmNovoRegistroComCoordenadasPreenchidas() {
        request.setLongitude("12");
        request.setLatitude("312");
        service.save(request);
        verify(googleMapsService, never()).setCoordinates(any(AddressRequest.class));
        verify(repository).saveAndFlush(any(Address.class));
    }

    @Test
    void deveFalharAoTentarInserirUmRegistroNovo() {
        doThrow(CommunicationException.class).when(googleMapsService).setCoordinates(any());
        assertThrows(CommunicationException.class, () -> service.save(request));
        verify(repository,never()).saveAndFlush(any(Address.class));
    }

    @Test
    void deveAtualizarUmEnderecoExistente() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(address));
        service.update(1L, request);
        verify(repository).saveAndFlush(any(Address.class));
    }

    @Test
    void deveAtualizarUmNovoRegistroComCoordenadasVazias() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(address));

        service.update(1L,request);

        verify(googleMapsService).setCoordinates(any(AddressRequest.class));
        verify(repository).saveAndFlush(any(Address.class));
    }

    @Test
    void deveAtualizarUmNovoRegistroComCoordenadasPreenchidas() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(address));
        request.setLongitude("12");
        request.setLatitude("312");

        service.update(1L,request);
        verify(googleMapsService, never()).setCoordinates(any(AddressRequest.class));
        verify(repository).saveAndFlush(any(Address.class));
    }

    @Test
    void deveLancarUmaExcecaoAoTentarAtualizarUmRegistroInexistent() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(1L, request));
        verify(repository, never()).saveAndFlush(any(Address.class));
    }
}