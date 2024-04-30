package com.attus.procuradoria.controller;

import com.attus.procuradoria.controller.documentation.ClientDocumentation;
import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/client")
@Slf4j
@RequiredArgsConstructor
public class ClientController implements ClientDocumentation {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ClientDTO createClient(Client client) {
        clientService.creatingClient(client);
        return null;
    }
}
