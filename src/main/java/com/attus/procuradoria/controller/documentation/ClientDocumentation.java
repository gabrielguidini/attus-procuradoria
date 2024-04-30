package com.attus.procuradoria.controller.documentation;

import com.attus.procuradoria.dto.ClientDTO;
import com.attus.procuradoria.entity.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ClientDocumentation {
    @Operation(summary = "Create a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create a client",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    ClientDTO createClient(Client Client);
}
