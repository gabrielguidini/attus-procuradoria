package com.attus.procuradoria.entity;

import com.attus.procuradoria.entity.enums.ClientAddressEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @Builder.Default
    private UUID addressId = UUID.randomUUID();
    private String streetName;
    private String zipCode;
    private String houseNumber;
    private String city;
    private String uf;
    @Enumerated(EnumType.STRING)
    private ClientAddressEnum clientAddressEnum;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
}
