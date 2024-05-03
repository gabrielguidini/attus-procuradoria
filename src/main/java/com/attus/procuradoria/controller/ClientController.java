package com.attus.procuradoria.controller;

import com.attus.procuradoria.controller.documentation.ClientDocumentation;
import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.exceptions.ClientNotFoundException;
import com.attus.procuradoria.exceptions.ViaCepException;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.service.AddressService;
import com.attus.procuradoria.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController("/client")
@Slf4j
@RequiredArgsConstructor
public class ClientController implements ClientDocumentation {

    private final ClientService clientService;
    private final AddressService addressService;
    private final ObjectMapper objectMapper;

    @GetMapping("/getAllClients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> getAllClients() {
        try {
            log.info("ClientController.getClientAllClients() -> init process");

            return clientService.findAllClients();
        } catch (ClientNotFoundException e){
            log.error("ClientController.getClientAllClients() -> error {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getAllClients/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClientById(@PathVariable UUID clientId) {
        try {
            log.info("ClientController.getClientById(UUID) -> init process, clientId = {}", clientId);

            return clientService.findClient(clientId);
        } catch (ClientNotFoundException e) {
            log.error("ClientController.getClientById(() -> error {}", clientId);
            throw new ClientNotFoundException(e.getMessage(), e.getCause());
        }
    }

    @PostMapping("/createClient")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ClientDTO createClient(@RequestBody ClientForm client,
                                  @RequestParam ClientAddressEnum clientAddressEnum,
                                  @RequestParam String zipCode,
                                  @RequestParam String houseNumber) throws JsonProcessingException {
        try {
            log.info("ClientController.createClient() -> init process , client {}", objectMapper.writeValueAsString(client));
            return clientService.creatingClient(client, clientAddressEnum, addressService.zipCodeFounder(zipCode, houseNumber));

        } catch (ViaCepException e) {
            log.error("ClientController.createClient() -> Error while trying to retrieve via-cep info {}", e.getMessage());
            throw new ViaCepException(e.getMessage());

        } catch (Exception e) {
            log.error("ClientController.createClient() -> Generic exception, clientForm {} ,error {}", this.objectMapper.writeValueAsString(client),e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ClientDTO deleteClient(@PathVariable UUID clientId) {

        try {
            log.info("ClientController.deleteClient() -> init process , clientUuid {}", clientId);

            ClientDTO clientDTO = clientService.deleteClient(clientId);

            log.info("ClientController.deleteClient() -> finish process , client {}", this.objectMapper.writeValueAsString(clientDTO));

            return clientDTO;
        } catch (ClientNotFoundException | JsonProcessingException e) {
            log.info("ClientController.deleteClient() -> client not found, client {}", clientId, e.getCause());
            throw new ClientNotFoundException(e.getMessage());
        }
    }

    @PutMapping("/addAddressIntoClient/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public List<AddressDTO> addAddressIntoClient(@PathVariable UUID clientId,
                                                 @RequestParam String zipCode,
                                                 @RequestParam String houseNumber,
                                                 @RequestParam ClientAddressEnum clientAddressEnum) {

        return clientService.addAddressIntoAClient(clientId,addressService.zipCodeFounder(zipCode, houseNumber), clientAddressEnum);
    }

}
