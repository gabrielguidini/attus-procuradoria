package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.repository.ClientRepository;
import com.attus.procuradoria.utils.ClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;

    public ClientDTO creatingClient(ClientForm clientForm,
                                    ClientAddressEnum clientAddressEnum,
                                    Address address) throws JsonProcessingException {

        log.info("ClientService.creatingClient() -> init process, client {}", this.objectMapper.writeValueAsString(clientForm) );

        Client client = ClientUtils.convertClientFormToClient(clientForm);

        UUID clientUuid = UUID.randomUUID();

        client.setClientUuid(clientUuid);

        address.setClientAddressEnum(clientAddressEnum);

        if(Optional.ofNullable(client.getClientAddress()).isEmpty()) {
            client.setClientAddress(List.of(address));
        } else {
            client.getClientAddress().add(address);
        }

        clientRepository.save(client);

        log.info("ClientService.creatingClient() -> finish process, client {}", this.objectMapper.writeValueAsString(client) );

        return ClientUtils.convertClientToClientDTO(client);
    }
}
