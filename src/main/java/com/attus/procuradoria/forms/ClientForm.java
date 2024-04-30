package com.attus.procuradoria.forms;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ClientForm {
    private UUID clientUuid;
    private String name;
    private String surname;
}
