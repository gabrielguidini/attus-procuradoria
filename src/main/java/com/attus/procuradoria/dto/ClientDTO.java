package com.attus.procuradoria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ClientDTO {
    private UUID clientId;
    private String name;
    private String surname;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private List<AddressDTO> clientAddress;
}
