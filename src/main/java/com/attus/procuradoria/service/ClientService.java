package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Client;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {
    public ClientDTO creatingClient(Client client) {

        UUID clientUuid = UUID.randomUUID();

        client.setClientUuid(clientUuid);



        return null;
    }
}
