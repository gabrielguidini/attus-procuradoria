package com.attus.procuradoria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private UUID clientUuid;
    private String name;
    private String surname;
    @OneToMany
    private List<Address> clientAdress;
}
