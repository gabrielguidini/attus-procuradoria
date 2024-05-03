package com.attus.procuradoria.arrange;

import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;

import java.util.UUID;

public class AddressArrange {

    public AddressArrange() {
    }

    public static Address getOneValidAddress() {
        return Address.builder()
                .addressId(UUID.fromString("60f9dddf-c4b1-40d7-ba5a-c868b4ccf76c"))
                .zipCode("89221543")
                .houseNumber("128")
                .clientAddressEnum(ClientAddressEnum.MAIN_ADDRESS)
                .build();
    }
}
