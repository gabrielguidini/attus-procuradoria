package com.attus.procuradoria.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    @Builder.Default
    private UUID clientUuid = UUID.randomUUID();
    private String name;
    private String surname;
    @OneToMany
    private List<Address> clientAddress;
}
