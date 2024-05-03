package com.attus.procuradoria.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @Builder.Default
    private UUID clientId = UUID.randomUUID();
    private String name;
    private String surname;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private List<Address> clientAddress;
}
