package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.AddressDTO;
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

        client.setBirthDate(clientForm.getBirthDate());

        address.setClientAddressEnum(clientAddressEnum);

        if(Optional.ofNullable(client.getClientAddress()).isEmpty()) {
            client.setClientAddress(List.of(address));
        } else {
            client.getClientAddress().add(address);
        }

        this.clientRepository.save(client);

        log.info("ClientService.creatingClient() -> finish process, client {}", this.objectMapper.writeValueAsString(client) );

        return ClientUtils.convertClientToClientDTO(client);
    }

    public ClientDTO deleteClient(UUID clientId) throws JsonProcessingException {
        log.info("ClientService.deleteClient() -> init process, clientUuid {}", clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE));

        clientRepository.delete(client);

        log.info("ClientService.deleteClient() -> finish process, client {} deleted", this.objectMapper.writeValueAsString(client));

        return ClientUtils.convertClientToClientDTO(client);
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

    public ClientDTO findClient(UUID clientId) {
        log.info("ClientService.findClient() -> init process, clientUuid {}", clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND_MESSAGE));

        log.info("ClientService.findClient() -> init process, clientUuid {}", clientId);

        return ClientUtils.convertClientToClientDTO(client);

    }

    public List<AddressDTO> addAddressIntoAClient(UUID clientId, Address address,ClientAddressEnum clientAddressEnum) {

        ClientDTO clientDTO = this.findClient(clientId);

        Client client = ClientUtils.convertClientDTOToClient(clientDTO);

        address.setClientAddressEnum(clientAddressEnum);

        client.getClientAddress().add(address);

        this.clientRepository.save(client);

        return clientDTO.getClientAddress();
    }
}
