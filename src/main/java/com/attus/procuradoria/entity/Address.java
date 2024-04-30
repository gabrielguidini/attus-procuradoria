package com.attus.procuradoria.entity;

import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer addressId;
    private String streetName;
    private String zipCode;
    private String houseNumber;
    private String city;
    private String uf;

    private ClientAddressEnum clientAddressEnum;
}
