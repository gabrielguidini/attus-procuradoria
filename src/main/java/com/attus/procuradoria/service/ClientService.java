package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.attus.procuradoria.repository.ClientRepository;
import com.attus.procuradoria.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientDTO creatingClient(ClientDTO clientDTO,
                                    ClientAddressEnum clientAddressEnum,
                                    AddressDTO addressDTO) {

        UUID clientUuid = UUID.randomUUID();

        clientDTO.setClientUuid(clientUuid);
        addressDTO.setClientAddressEnum(clientAddressEnum);
        clientDTO.setClientAddress(List.of(addressDTO));

        clientRepository.save(ClientUtils.convertClientToClientDTO(clientDTO));

        return clientDTO;
    }
}
