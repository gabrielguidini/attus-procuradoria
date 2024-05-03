package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.entity.Address;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    Address createAddress(String zipCode, String houseNumber) throws JsonProcessingException;

    List<AddressDTO> findAllAddresses();

    AddressDTO updateAddress(UUID addressUuid, String newZipCode, String newHouseNumber) throws JsonProcessingException;
}
