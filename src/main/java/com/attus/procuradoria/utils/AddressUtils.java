package com.attus.procuradoria.utils;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ViaCepDTO;
import com.attus.procuradoria.entity.Address;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressUtils {

    public Address convertViaCepDTOtoAddress(ViaCepDTO viaCepDTO){

        return Address.builder()
                .zipCode(viaCepDTO.getCep())
                .streetName(viaCepDTO.getLogradouro())
                .houseNumber(viaCepDTO.getComplemento())
                .city(viaCepDTO.getLocalidade())
                .uf(viaCepDTO.getUf())
                .build();
    }

    public AddressDTO convertAddressToAddressDTO(Address address){

        return AddressDTO.builder()
                .zipCode(address.getZipCode())
                .streetName(address.getStreetName())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .clientAddressEnum(address.getClientAddressEnum())
                .uf(address.getUf())
                .build();
    }

    public Address convertAddressDTOToAddress(AddressDTO addressDTO){

        return Address.builder()
                .zipCode(addressDTO.getZipCode())
                .streetName(addressDTO.getStreetName())
                .houseNumber(addressDTO.getHouseNumber())
                .city(addressDTO.getCity())
                .clientAddressEnum(addressDTO.getClientAddressEnum())
                .uf(addressDTO.getUf())
                .build();
    }

}
