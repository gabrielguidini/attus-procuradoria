package com.attus.procuradoria.controller;

import com.attus.procuradoria.controller.documentation.AddressDocumentation;
import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.exceptions.AddressNotFoundException;
import com.attus.procuradoria.service.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController implements AddressDocumentation {

    private final AddressService addressService;
    private final String ADDRESS_NOT_FOUND_MESSAGE = "Address not found";

    @GetMapping("/getAllAddress")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDTO> getAllAddress() throws JsonProcessingException {
        log.info("AddressController.getAllAddress() -> init process");

        try {
            return addressService.findAllAddresses();
        } catch (AddressNotFoundException e) {
            log.error("AddressController.getAllAddress() -> error {}", e.getMessage());
            throw new AddressNotFoundException(ADDRESS_NOT_FOUND_MESSAGE);
        }
    }

    @PutMapping("/{addressUuid}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO updateAddress(@PathVariable UUID addressUuid,
                                          @RequestParam String newZipCode,
                                          @RequestParam String newHouseNumber) throws JsonProcessingException {

        return addressService.updateAddress(addressUuid, newZipCode, newHouseNumber);
    }

}