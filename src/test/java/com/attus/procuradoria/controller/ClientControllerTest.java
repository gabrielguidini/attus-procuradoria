package com.attus.procuradoria.controller;

import com.attus.procuradoria.arrange.ClientArrange;
import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.exceptions.AddressNotFoundException;
import com.attus.procuradoria.exceptions.ClientNotFoundException;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.service.ClientService;
import com.attus.procuradoria.utils.ClientUtils;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class ClientControllerTest {
    @InjectMocks
    private ClientController clientController;
    @Mock
    private ClientService clientService;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        clientController = new ClientController(clientService, objectMapper);
    }

    @Test
    public void shouldGetAllClients() throws JsonProcessingException {
        //arrange
        Client client = ClientArrange.getValidClientWithOneAddress();
        when(clientService.findAllClients()).thenReturn(Stream.of(client).map(ClientUtils::convertClientToClientDTO).toList());

        //act
        var response = clientController.getAllClients();
        //assert
        assertNotNull(response);
        assertEquals(this.objectMapper.writeValueAsString(client),
                this.objectMapper.writeValueAsString(response));


    }

    @Test
    public void shouldThrowClientNotFoundException() {
        //arrange
        String NOT_FOUND_MESSAGE = "Client not found";
        when(clientService.findAllClients()).thenThrow(ClientNotFoundException.class);

        //act
        var exception = assertThrows(ClientNotFoundException.class,
                () -> clientController.getAllClients()).getMessage();

        //assert
        assertEquals(NOT_FOUND_MESSAGE, exception);

    }

    @Test
    public void shoulGetClientById() {
        //arrange
        ClientDTO client = ClientUtils.convertClientToClientDTO(ClientArrange.getValidClientWithOneAddress());
        when(clientService.findClient(client.getClientId()))
                .thenReturn(client);

        //act
        var response = clientController.getClientById(client.getClientId());

        //assert
        assertNotNull(response);
        assertEquals(client, response);

    }

    @Test
    public void shouldThrowClientNotFoundExceptionById() {
        //arrange
        String NOT_FOUND_MESSAGE = "Client not found";
        when(clientService.findClient(any(UUID.class))).thenThrow(ClientNotFoundException.class);

        //act
        var exception = assertThrows(ClientNotFoundException.class,
                () -> clientController.getClientById(UUID.randomUUID()));

        //assert
        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());

    }

    @Test
    public void shouldCreateClient() throws JsonProcessingException {
        //arrange
        ClientForm clientForm = ClientArrange.getValidClientForm();
        ClientDTO client = ClientUtils.convertClientToClientDTO(ClientArrange.getValidClientWithOneAddress());
        when(clientService.creatingClient(clientForm,
                ClientAddressEnum.MAIN_ADDRESS,"89221543", "128")).thenReturn(client);

        //act
        var response = clientController.createClient(clientForm, ClientAddressEnum.MAIN_ADDRESS,
                "89221543", "128");

        //assert
        assertNotNull(response);
        assertEquals(client, response);

    }

    @Test
    public void shouldThrowAddressNotFoundException() throws JsonProcessingException {
        //arrange
        String NOT_FOUND_MESSAGE = "Address not found";
        ClientForm clientForm = ClientArrange.getValidClientForm();

        when(clientService.creatingClient(any(ClientForm.class),any(ClientAddressEnum.class)
                        ,any(String.class),any(String.class))).thenThrow(AddressNotFoundException.class);

        //act
        var exception = assertThrows(AddressNotFoundException.class,
                () -> clientController.createClient(clientForm,ClientAddressEnum.MAIN_ADDRESS,"89221543","128"));

        //assert
        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());

    }

    @Test
    public void shouldDeleteClient() throws JsonProcessingException {
        //arrange
        ClientDTO client = ClientUtils.convertClientToClientDTO(ClientArrange.getValidClientWithOneAddress());
        when(clientService.deleteClient(client.getClientId())).thenReturn(client);

        //act
        var response = clientController.deleteClient(client.getClientId());

        //assert
        assertNotNull(response);
        assertEquals(client, response);

    }

    @Test
    public void shouldThrowClientNotFoundExceptionOnDeleteClient() throws JsonProcessingException {
        //arrange
        String NOT_FOUND_MESSAGE = "Client not found";
        when(clientService.deleteClient(any(UUID.class)))
                .thenThrow(ClientNotFoundException.class);

        //act
        var response = assertThrows(ClientNotFoundException.class,
                () -> clientController.deleteClient(UUID.randomUUID())).getMessage();

        //assert
        assertEquals(NOT_FOUND_MESSAGE, response);

    }

    @Test
    public void shouldAddAddressIntoClient() throws JsonProcessingException {
        //arrange
        ClientDTO clientBefore = ClientUtils.convertClientToClientDTO(ClientArrange.getValidClientWithOneAddress());
        ClientDTO clientAfter = ClientUtils.convertClientToClientDTO(ClientArrange.getValidClientWithTwoAddress());
        when(clientService.addAddressIntoAClient(clientBefore.getClientId(), "89213480", "656", ClientAddressEnum.SECONDARY_ADDRESS))
                .thenReturn(clientAfter.getClientAddress());


        //act
        var responseAddIntoClient = clientController.addAddressIntoClient(clientBefore.getClientId(),
                "89213480", "656", ClientAddressEnum.SECONDARY_ADDRESS);

        //assert
        assertNotNull(responseAddIntoClient);
        assertEquals(clientAfter.getClientAddress(), responseAddIntoClient);

    }

}
