package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ViaCepDTO;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.entity.Client;
import com.attus.procuradoria.exceptions.AddressNotFoundException;
import com.attus.procuradoria.exceptions.ClientNotFoundException;
import com.attus.procuradoria.exceptions.ViaCepException;
import com.attus.procuradoria.repository.AddressRepository;
import com.attus.procuradoria.utils.AddressUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;
    private final String ADDRESS_NOT_FOUND_MESSAGE = "Address not found";


    public Address zipCodeFounder(String comingZipCode, String houseNumber) {

        log.info("AddressService.zipCodeFounder() -> init process, zipCode {}, houseNumber {}", comingZipCode, houseNumber);

        ViaCepDTO viaCep = null;

        HttpGet request = new HttpGet("https://viacep.com.br/ws/"+ comingZipCode +"/json/");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();
                viaCep = gson.fromJson(result, ViaCepDTO.class);
            }
            if (viaCep == null) {
                log.error("AddressService.zipCodeFounder() -> Zip code not found, zipCode {}", comingZipCode);
                throw new ViaCepException("Zip Code Info is Empty");
            }
            viaCep.setComplemento(houseNumber);

            Address updatedAddress = AddressUtils.convertViaCepDTOtoAddress(viaCep);

            this.addressRepository.save(updatedAddress);

            log.info("AddressService.zipCodeFounder() -> finish process, address {}", objectMapper.writeValueAsString(updatedAddress));

            return updatedAddress;

        } catch (ViaCepException e) {
            log.error("AddressService.zipCodeFounder() -> error while trying to retrieve zip code info, zipCode {} ,error {}", comingZipCode, e.getMessage());
            throw new ViaCepException(e.getMessage());
        } catch (IOException e) {
            log.error("AddressService.zipCodeFounder() -> error IOException {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
