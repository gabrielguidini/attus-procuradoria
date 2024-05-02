package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.exceptions.ClientNotFoundException;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.repository.ClientRepository;
import com.attus.procuradoria.utils.ClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;
    private final String CLIENT_NOT_FOUND_MESSAGE = "Client not found";

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

    public ClientDTO deleteClient(UUID clientUuid) throws JsonProcessingException {
        log.info("ClientService.deleteClient() -> init process, clientUuid {}", clientUuid);

        Client client = clientRepository.findByClientUuid(clientUuid)
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE));

        clientRepository.delete(client);

        log.info("ClientService.deleteClient() -> finish process, client {} deleted", this.objectMapper.writeValueAsString(client));

        return ClientUtils.convertClientToClientDTO(client);
    }

    public ClientDTO updateClient(Client updatedClient) throws JsonProcessingException {
        log.info("ClientService.updateClient() -> init process, client {}", this.objectMapper.writeValueAsString(updatedClient));
        Client existingClient = clientRepository.findByClientUuid(updatedClient.getClientUuid())
                .orElseThrow(()-> new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE));

            Map<String, Object> updatedClientMap = objectMapper.convertValue(updatedClient, Map.class);

            updatedClientMap.values().removeIf(Objects::isNull);

            objectMapper.readerForUpdating(existingClient).readValue(objectMapper.writeValueAsString(updatedClientMap));

            clientRepository.save(existingClient);

            return ClientUtils.convertClientToClientDTO(existingClient);
    }

    public List<ClientDTO> findAllClients() {
        log.info("ClientService.findAllClients() -> init process");

        List<ClientDTO> clientDTOList = clientRepository.findAll()
                .stream()
                .map(ClientUtils::convertClientToClientDTO)
                .toList();

        clientDTOList.stream().findAny().orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE));

        log.info("ClientService.findAllClients() -> finish process");

        return clientDTOList;
    }

    public ClientDTO findClient(UUID clientUuid) {
        log.info("ClientService.findClient() -> init process, clientUuid {}", clientUuid);

        Client client = clientRepository.findByClientUuid(clientUuid)
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE));

        log.info("ClientService.findClient() -> init process, clientUuid {}", clientUuid);

        return ClientUtils.convertClientToClientDTO(client);

    }
}
