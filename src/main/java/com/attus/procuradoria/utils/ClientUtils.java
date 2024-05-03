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
                .clientId(client.getClientId())
                .name(client.getName())
                .surname(client.getSurname())
                .clientAddress(client.getClientAddress()
                        .stream()
                        .map(AddressUtils::convertAddressToAddressDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public Client convertClientDTOToClient(ClientDTO clientDTO) {

        return Client.builder()
                .clientId(clientDTO.getClientId())
                .name(clientDTO.getName())
                .surname(clientDTO.getSurname())
                .clientAddress(clientDTO.getClientAddress()
                        .stream()
                        .map(AddressUtils::convertAddressDTOToAddress)
                        .collect(Collectors.toList()))
                .build();
    }

    public Client convertClientFormToClient(ClientForm clientForm) {

        return Client.builder()
                .name(clientForm.getName())
                .surname(clientForm.getSurname())
                .birthDate(clientForm.getBirthDate())
                .build();
    }

}
