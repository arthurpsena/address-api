package com.stoom.addressapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoom.addressapi.exception.NotFoundException;
import com.stoom.addressapi.exception.handler.AddressExceptionHandler;
import com.stoom.addressapi.model.Address;
import com.stoom.addressapi.model.dto.request.AddressRequest;
import com.stoom.addressapi.model.dto.response.AddressResponse;
import com.stoom.addressapi.service.AddressService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AddressController.class)
@ContextConfiguration(classes = {AddressController.class, AddressExceptionHandler.class})
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService service;

    private AddressResponse addressResponse;

    private static ObjectMapper mapper;

    private AddressRequest request;

    @BeforeAll
    static void beforeAll() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    void setUp() {

        addressResponse = new AddressResponse(Address.builder()
                .id(1L)
                .city("São Paulo")
                .country("Brasil")
                .neighbourhood("Vila Mariana")
                .number("143")
                .streetName("Abilio Soares")
                .zipcode("06026000")
                .state("SP")
                .build());

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
    void deveListarOsEnderecos() throws Exception {
        when(service.findAll(any(), any())).thenReturn(new PageImpl<>(List.of(addressResponse)));
        mockMvc.perform(
                get("/addresses").content(mapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deveAcharUmEndereco() throws Exception {
        when(service.findById(anyLong())).thenReturn(addressResponse);
        mockMvc.perform(
                get("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deveLancarStatusCode_404() throws Exception {
        when(service.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(
                get("/addresses/98")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void deveValidarSeEnderecoEstaDevidamentePreenchido() throws Exception {
        request.setCity(null);
        mockMvc.perform(
                post("/addresses").content(mapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
        verify(service, never()).save(any());
    }

    @Test
    void deveSalvarUmNovoEndereco() throws Exception {
        when(service.save(any())).thenReturn(addressResponse);
        mockMvc.perform(
                post("/addresses").content(mapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void deveAtualizarUmEndereco() throws Exception {
        when(service.update(anyLong(), any())).thenReturn(addressResponse);
        mockMvc.perform(
                put("/addresses/1").content(mapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void deveDeletarUmEndereco() throws Exception {
        mockMvc.perform(
                delete("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
    }
}