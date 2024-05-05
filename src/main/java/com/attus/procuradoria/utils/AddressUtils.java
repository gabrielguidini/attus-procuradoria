package com.attus.procuradoria.utils;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ViaCepDTO;
import com.attus.procuradoria.entity.Address;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressUtils {

    public Address convertViaCepDTOtoAddress(ViaCepDTO viaCepDTO){

        return Address.builder()
                .streetName(viaCepDTO.getLogradouro())
                .zipCode(viaCepDTO.getCep())
                .houseNumber(viaCepDTO.getComplemento())
                .city(viaCepDTO.getLocalidade())
                .uf(viaCepDTO.getUf())
                .build();
    }

    public AddressDTO convertAddressToAddressDTO(Address address){

        return AddressDTO.builder()
                .addressId(address.getAddressId())
                .streetName(address.getStreetName())
                .zipCode(address.getZipCode())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .clientAddressEnum(address.getClientAddressEnum())
                .uf(address.getUf())
                .build();
    }

    public Address convertAddressDTOToAddress(AddressDTO addressDTO){

        return Address.builder()
                .addressId(addressDTO.getAddressId())
                .streetName(addressDTO.getStreetName())
                .zipCode(addressDTO.getZipCode())
                .houseNumber(addressDTO.getHouseNumber())
                .city(addressDTO.getCity())
                .clientAddressEnum(addressDTO.getClientAddressEnum())
                .uf(addressDTO.getUf())
                .build();
    }

}
