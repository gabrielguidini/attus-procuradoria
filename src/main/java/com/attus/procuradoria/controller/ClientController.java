package com.attus.procuradoria.controller;

import com.attus.procuradoria.controller.documentation.ClientDocumentation;
import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.exceptions.ViaCepException;
import com.attus.procuradoria.forms.ClientForm;
import com.attus.procuradoria.service.AddressService;
import com.attus.procuradoria.service.ClientService;
import com.attus.procuradoria.utils.ClientUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ClientDTO createClient(@RequestBody @NotNull ClientForm clientForm,
                                  @RequestParam ClientAddressEnum clientAddressEnum,
                                  @RequestParam String zipCode,
                                  @RequestParam String houseNumber) {
        try {
            log.info("");
            return clientService.creatingClient(ClientUtils.convertClientFormToClientDTO(clientForm),
                    clientAddressEnum,
                    addressService.zipCodeFounder(zipCode, houseNumber));

        } catch (ViaCepException e) {
            log.error("");
            throw new ViaCepException(e.getMessage());

        } catch (Exception e) {
            log.error("");
            throw new RuntimeException(e);
        }
    }
}
