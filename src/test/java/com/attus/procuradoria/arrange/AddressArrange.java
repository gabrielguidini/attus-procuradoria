package com.attus.procuradoria.arrange;

import com.attus.procuradoria.entity.Address;

import java.util.List;
import java.util.UUID;

public class AddressArrange {

    public AddressArrange() {
    }

    public static Address getOneValidAddress() {
        return Address.builder()
                .addressId(UUID.fromString("60f9dddf-c4b1-40d7-ba5a-c868b4ccf76c"))
                .streetName("Rua Tenente Antônio João")
                .zipCode("89221543")
                .houseNumber("128")
                .city("Joinville")
                .uf("SC")
                .build();
    }

    public static List<Address> getListOfValidAddresses() {
        return List.of(getOneValidAddress());
    }
}
