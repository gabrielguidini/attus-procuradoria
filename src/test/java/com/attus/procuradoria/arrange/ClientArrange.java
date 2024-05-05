package com.attus.procuradoria.arrange;

import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.forms.ClientForm;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class ClientArrange {

    public ClientArrange() {
    }

    public static Client getValidClientWithOneAddress() {
        return Client.builder()
                .clientId(UUID.fromString("d9ba5e2f-60be-4fa4-a641-0a7c4ee3cdd8"))
                .name("Test")
                .surname("Test")
                .birthDate(Date.valueOf("1999-09-08"))
                .clientAddress(List.of(AddressArrange.getOneValidAddress()))
                .build();

    }

    public static ClientForm getValidClientForm() {
        return ClientForm.builder()
                .name("Test")
                .surname("Test")
                .birthDate(Date.valueOf("1999-09-08"))
                .build();
    }
}
