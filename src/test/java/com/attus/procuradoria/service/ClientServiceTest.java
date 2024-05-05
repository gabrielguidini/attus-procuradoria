package com.attus.procuradoria.service;

import com.attus.procuradoria.arrange.ClientArrange;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    public void shouldCreateClient() {
        //arrange
        ClientForm clientForm = ClientArrange.getValidClientForm();
        Client actualClient = ClientArrange.getValidClientWithOneAddress();



        //act

        //assert

    }

}
