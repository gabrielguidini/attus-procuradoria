package com.attus.procuradoria.controller;

import com.attus.procuradoria.controller.documentation.AddressDocumentation;
import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.service.implementation.AddressServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private final AddressServiceImp addressServiceImp;

    @GetMapping("/getAllAddress")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDTO> getAllAddress() {

        return addressServiceImp.findAllAddresses();
    }

    @PutMapping("/{addressUuid}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO updateAddress(@PathVariable UUID addressUuid,
                                          @RequestParam String newZipCode,
                                          @RequestParam String newHouseNumber) throws JsonProcessingException {

        return addressServiceImp.updateAddress(addressUuid, newZipCode, newHouseNumber);
    }

}