package com.attus.procuradoria.service;

import com.attus.procuradoria.arrange.AddressArrange;
import com.attus.procuradoria.arrange.ClientArrange;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.exceptions.ClientNotFoundException;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.repository.ClientRepository;
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
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AddressService addressService;

    @BeforeEach
    public void setup() {
        clientService = new ClientService(clientRepository,addressService, objectMapper);
    }

    @Test
    public void shouldCreateClient() throws JsonProcessingException {
        //arrange
        ClientForm clientForm = ClientArrange.getValidClientForm();
        Client actualClient = ClientArrange.getValidClientWithOneAddress();
        Address address = AddressArrange.getOneValidAddress();

        when(addressService.createAddress("89221543","128")).thenReturn(address);


        //act
        var response = clientService.creatingClient(clientForm,address.getClientAddressEnum(),
                address.getZipCode(),address.getHouseNumber());

        //assert
        assertNotNull(response);
        assertEquals(this.objectMapper.writeValueAsString(actualClient),
                this.objectMapper.writeValueAsString(response));
    }

    @Test
    public void shouldDeleteClient() throws JsonProcessingException {
        //arrange
        Client actualClient = ClientArrange.getValidClientWithOneAddress();
        when(clientRepository.findById(actualClient.getClientId())).thenReturn(Optional.of(actualClient));

        //act
        var response = clientService.deleteClient(actualClient.getClientId());

        //assert
        assertNotNull(response);
        assertEquals(this.objectMapper.writeValueAsString(actualClient),
                this.objectMapper.writeValueAsString(response));
    }
    @Test
    public void shouldThrowClientNotFoundWhenDeletingAClient() throws JsonProcessingException {
        //arrange
        Client actualClient = ClientArrange.getValidClientWithOneAddress();
        String NOT_FOUND_MESSAGE = "Client not found";

        //act
        var exception = assertThrows(ClientNotFoundException.class,
                () -> clientService.deleteClient(any(UUID.class))).getMessage();

        //assert
        assertNotNull(exception);
        assertEquals(NOT_FOUND_MESSAGE,exception);
    }


}
