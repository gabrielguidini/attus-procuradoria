package com.attus.procuradoria.service;

import com.attus.procuradoria.arrange.AddressArrange;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.exceptions.AddressNotFoundException;
import com.attus.procuradoria.repository.AddressRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    public void setup() {
        addressService = new AddressService(addressRepository, objectMapper);
    }

    @Test
    public void shouldCreateAndReturnAValidAddress() throws JsonProcessingException {
        //arrange
        String zipCode = "89221543";
        String houseNumber = "128";
        Address actualAddress = AddressArrange.getOneValidAddress();

        //act
        var response = addressService.createAddress(zipCode, houseNumber);

        //assert
        assertNotNull(response);
        assertEquals(objectMapper.writeValueAsString(response), objectMapper.writeValueAsString(actualAddress));

    }

    @Test
    public void shouldThrowAddressNotFoundException()  {
        //arrange
        String zipCode = "00000000";
        String houseNumber = "000";
        String actualExceptionMessage = "Address not found";


        //act
        Exception exception = assertThrows(AddressNotFoundException.class,
        () ->addressService.zipCodeFounder(zipCode,houseNumber));

        //assert
        assertNotNull(exception);
        assertEquals(actualExceptionMessage,exception.getMessage());
    }

    @Test
    public void shouldReturnAllAddresses() throws JsonProcessingException {
        //arrange

        //act
        var response = addressService.findAllAddresses();

        //assert
        assertNotNull(response);

    }

    @Test
    public void shouldUpdateAddress() throws JsonProcessingException {
        //arrange
        Address address = AddressArrange.getOneValidAddress();
        String newZipCode = "89213480";
        String newHouseNumber = "656";
        when(addressRepository.findById(UUID.fromString("60f9dddf-c4b1-40d7-ba5a-c868b4ccf76c"))).thenReturn(Optional.of(address));


        //act
        var response = addressService.updateAddress(address.getAddressId(),newZipCode, newHouseNumber);

        //assert
        assertNotNull(response);
        assertEquals(objectMapper.writeValueAsString(response),
                objectMapper.writeValueAsString(address));

    }

}
