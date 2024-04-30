package com.attus.procuradoria.utils;

import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.forms.ClientForm;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class ClientUtils {

    public ClientDTO convertClientToClientDTO(Client client) {

        return ClientDTO.builder()
                .clientUuid(client.getClientUuid())
                .name(client.getName())
                .surname(client.getSurname())
                .clientAddress(client.getClientAddress()
                        .stream()
                        .map(AddressUtils::convertAddressToAddressDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ClientDTO convertClientFormToClientDTO(ClientForm clientForm) {

        return ClientDTO.builder()
                .clientUuid(null)
                .name(clientForm.getName())
                .surname(clientForm.getSurname())
                .clientAddress(null)
                .build();
    }

    public Client convertClientToClientDTO(ClientDTO client) {

        return Client.builder()
                .clientUuid(client.getClientUuid())
                .name(client.getName())
                .surname(client.getSurname())
                .clientAddress(client.getClientAddress()
                        .stream()
                        .map(AddressUtils::convertAddressDTOToAddress)
                        .collect(Collectors.toList()))
                .build();
    }

}
