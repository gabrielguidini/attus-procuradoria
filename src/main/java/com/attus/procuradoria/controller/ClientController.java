package com.attus.procuradoria.controller;

import com.attus.procuradoria.controller.documentation.ClientDocumentation;
import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.exceptions.ViaCepException;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.service.AddressService;
import com.attus.procuradoria.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/client")
@Slf4j
@RequiredArgsConstructor
public class ClientController implements ClientDocumentation {

    private final ClientService clientService;
    private final AddressService addressService;
    private final ObjectMapper objectMapper;

    @PostMapping("/createClient")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ClientDTO createClient(@RequestBody ClientForm client,
                                  @RequestParam ClientAddressEnum clientAddressEnum,
                                  @RequestParam String zipCode,
                                  @RequestParam String houseNumber) {
        try {
            log.info("ClientController.createClient() -> init process , client {}", objectMapper.writeValueAsString(client));
            return clientService.creatingClient(client, clientAddressEnum, addressService.zipCodeFounder(zipCode, houseNumber));

        } catch (ViaCepException e) {
            log.error("problema no viacep");
            throw new ViaCepException(e.getMessage());

        } catch (Exception e) {
            log.error("generic exception");
            throw new RuntimeException(e);
        }
    }
}
