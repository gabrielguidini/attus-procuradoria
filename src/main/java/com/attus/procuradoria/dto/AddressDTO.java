package com.attus.procuradoria.dto;

import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import lombok.*;

@Builder
@Getter
@Setter
public class AddressDTO {
    private String zipCode;
    private String streetName;
    private String houseNumber;
    private String city;
    private String uf;
    private ClientAddressEnum clientAddressEnum;

}
