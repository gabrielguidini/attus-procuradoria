package com.attus.procuradoria.service;

import com.attus.procuradoria.arrange.AddressArrange;
import com.attus.procuradoria.arrange.ClientArrange;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.repository.ClientRepository;
import com.attus.procuradoria.utils.AddressUtils;
import com.attus.procuradoria.utils.ClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class ClientServiceTest {
    @Mock
    private ClientService clientService;
    @Mock
    private AddressService addressService;


    @Test
    public void shouldReturnCreateAClient() throws JsonProcessingException {
        //arrange
        ClientForm clientForm = ClientArrange.getValidClientForm();
        Address address = AddressArrange.getOneValidAddress();
        when(addressService.createAddress(address.getZipCode(), address.getCity())).thenReturn(address);
        when(clientService.creatingClient(clientForm,address.getClientAddressEnum(), address.getZipCode(), address.getHouseNumber()))
                .thenReturn(ClientUtils.convertClientToClientDTO(ClientArrange.getValidClientWithOneAddress()));

        //act
        var response = clientService.creatingClient(clientForm, address.getClientAddressEnum(), address.getZipCode(), address.getHouseNumber());

        //assert
        assertNotNull(response);
        assertTrue(response.getClientAddress().stream().anyMatch(addressDTO -> addressDTO.equals(AddressUtils.convertAddressToAddressDTO(address))));

    }


}
