//package com.attus.procuradoria.controller;
//
//import com.attus.procuradoria.controller.documentation.AddressDocumentation;
//import com.attus.procuradoria.dto.AddressDTO;
//import com.attus.procuradoria.entity.Address;
//import com.attus.procuradoria.service.AddressService;
//import com.attus.procuradoria.utils.AddressUtils;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController("/address")
//@RequiredArgsConstructor
//@Slf4j
//public class AddressController implements AddressDocumentation {
//
//    private final AddressService addressService;
//
//    @GetMapping("/getAllAddress")
//    @ResponseStatus(HttpStatus.OK)
//    public List<AddressDTO> getAllAddress() {
//
//        return addressService.findAllAddresses();
//    }
//
//    @GetMapping("/getAllAddress/{clientUuid}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<AddressDTO> getAddressByClientUuid(@PathVariable UUID clientUuid) {
//
//        return addressService.findAddressesByClientUuid(clientUuid);
//    }
//
//    @PostMapping("/createAddress")
//    @Transactional
//    @ResponseStatus(HttpStatus.CREATED)
//    public AddressDTO createAddress(@RequestParam String zipCode, @RequestParam String houseNumber) {
//
//        Address address = addressService.createAddress(zipCode, houseNumber);
//
//        return AddressUtils.convertAddressToAddressDTO(address);
//    }
//
//}