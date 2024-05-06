package com.attus.procuradoria.arrange;

import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.enums.ClientAddressEnum;

import java.util.ArrayList;
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

    public static Address getSecondValidAddress() {
        return Address.builder()
                .addressId(UUID.fromString("912bf7d4-5f46-4696-abb1-c54e72473ccb"))
                .zipCode("89213480")
                .streetName("Rua Cerro Azul")
                .houseNumber("656")
                .clientAddressEnum(ClientAddressEnum.SECONDARY_ADDRESS)
                .city("Joinville")
                .uf("SC")
                .build();
    }

    public static List<Address> getTwoValidAddresses() {
        Address addressOne = Address.builder()
                .addressId(UUID.fromString("60f9dddf-c4b1-40d7-ba5a-c868b4ccf76c"))
                .streetName("Rua Tenente Antônio João")
                .zipCode("89221543")
                .houseNumber("128")
                .city("Joinville")
                .uf("SC")
                .build();

        Address addressTwo = Address.builder()
                .addressId(UUID.fromString("912bf7d4-5f46-4696-abb1-c54e72473ccb"))
                .zipCode("89213480")
                .streetName("Rua Cerro Azul")
                .houseNumber("656")
                .clientAddressEnum(ClientAddressEnum.SECONDARY_ADDRESS)
                .city("Joinville")
                .uf("SC")
                .build();

        List<Address> addresses = new ArrayList<>();
        addresses.add(addressOne);
        addresses.add(addressTwo);

        return addresses;
    }

    public static List<Address> getListOfValidAddresses() {
        return List.of(getOneValidAddress());
    }
}
