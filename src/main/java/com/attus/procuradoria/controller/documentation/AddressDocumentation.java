package com.attus.procuradoria.controller.documentation;


import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.entity.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface AddressDocumentation {
    @Operation(summary = "Get All Addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get All Addresses",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    List<AddressDTO> getAllAddress();

    @Operation(summary = "Update an Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Update an Address",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    AddressDTO updateAddress(UUID addressUuid,
                             String newZipCode,
                             String newHouseNumber) throws JsonProcessingException;
}
