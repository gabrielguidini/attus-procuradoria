package com.attus.procuradoria.dto;

import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
public class AddressDTO {
    private UUID addressId;
    private String zipCode;
    private String streetName;
    private String houseNumber;
    private String city;
    private String uf;
    private ClientAddressEnum clientAddressEnum;
    private Client client;

}
