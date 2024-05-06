package com.attus.procuradoria.controller;

import com.attus.procuradoria.arrange.AddressArrange;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.exceptions.AddressNotFoundException;
import com.attus.procuradoria.service.AddressService;
import com.attus.procuradoria.utils.AddressUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class AddressControllerTest {
    @InjectMocks
    private AddressController addressController;
    @Mock
    private AddressService addressService;
    @Mock
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        addressController = new AddressController(addressService);

    }

    @Test
    public void shouldReturnAllAddresses() throws Exception {
        //arrange
        List<Address> addressList = AddressArrange.getTwoValidAddresses();

        when(addressService.findAllAddresses())
                .thenReturn(addressList.stream().map(AddressUtils::convertAddressToAddressDTO).toList());

        //act
        var response = addressController.getAllAddress();

        //assert
        assertNotNull(response);
        assertEquals(this.objectMapper.writeValueAsString(addressList),
                this.objectMapper.writeValueAsString(response));


    }

    @Test
    public void shouldThrowAddressNotFound() throws Exception {
        //arrange
        String ADDRESS_NOT_FOUND_MESSAGE = "Address not found";
        when(addressService.findAllAddresses()).thenThrow(AddressNotFoundException.class);

        //act
        var exception = assertThrows(AddressNotFoundException.class,
                () -> addressController.getAllAddress()).getMessage();

        //assert
        assertNotNull(exception);
        assertEquals(ADDRESS_NOT_FOUND_MESSAGE, exception);


    }
    @Test
    public void shouldUpdateAddress() throws Exception {
        //arrange
        Address address = AddressArrange.getOneValidAddress();
        String newZipCode = "89213480";
        String newHouseNumber = "656";
        Address newAddress = AddressArrange.getSecondValidAddress();
        when(addressService.updateAddress(address.getAddressId(), newZipCode, newHouseNumber))
                .thenReturn(AddressUtils.convertAddressToAddressDTO(newAddress));


        //act
        var response = addressController.updateAddress(address.getAddressId(),newZipCode, newHouseNumber);

        //assert
        assertNotNull(response);
        assertEquals(newAddress.getAddressId(),response.getAddressId());
        assertEquals(newAddress.getZipCode(),response.getZipCode());
        assertEquals(newAddress.getHouseNumber(),response.getHouseNumber());
        assertEquals(newAddress.getCity(),response.getCity());
        assertEquals(newAddress.getStreetName(), response.getStreetName());

    }

}


