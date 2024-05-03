package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.forms.ClientForm;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ClientDTO creatingClient(ClientForm clientForm,
                                    ClientAddressEnum clientAddressEnum,
                                    String zipCode, String houseNumber) throws JsonProcessingException;

    ClientDTO deleteClient(UUID clientId) throws JsonProcessingException;

    List<ClientDTO> findAllClients();

    ClientDTO findClient(UUID clientId);

    List<AddressDTO> addAddressIntoAClient(UUID clientId, String zipCode,String houseNumber, ClientAddressEnum clientAddressEnum) throws JsonProcessingException;
}
