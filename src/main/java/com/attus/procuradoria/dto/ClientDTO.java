package com.attus.procuradoria.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ClientDTO {
    private UUID clientUuid;
    private String name;
    private String surname;
    private List<AddressDTO> clientAddress;
}
