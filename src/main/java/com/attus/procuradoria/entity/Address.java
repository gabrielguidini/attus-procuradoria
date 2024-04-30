package com.attus.procuradoria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;
    private String streetName;
    private String zipCode;
    private String houseNumber;
    private String city;
    private String uf;
}
