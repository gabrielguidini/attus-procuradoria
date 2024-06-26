package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ViaCepDTO;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.exceptions.AddressNotFoundException;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;
    private final String ADDRESS_NOT_FOUND_MESSAGE = "Address not found";

    public Address createAddress(String zipCode, String houseNumber) throws JsonProcessingException {

        Address newAddress = this.zipCodeFounder(zipCode, houseNumber);

        this.save(newAddress);

        return newAddress;
    }

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
                throw new ViaCepException("Address not found in ViaCep API");
            }
            viaCep.setComplemento(houseNumber);

            Address updatedAddress = AddressUtils.convertViaCepDTOtoAddress(viaCep);

            if(updatedAddress.getZipCode() == null) {
                throw new AddressNotFoundException(ADDRESS_NOT_FOUND_MESSAGE);
            }

            log.info("AddressService.zipCodeFounder() -> finish process, address {}", objectMapper.writeValueAsString(updatedAddress));

            return updatedAddress;

        } catch (AddressNotFoundException e) {
            log.error("AddressService.zipCodeFounder() -> error while trying to retrieve zip code info, zipCode {} ,error {}", comingZipCode, e.getMessage());
            throw new AddressNotFoundException(ADDRESS_NOT_FOUND_MESSAGE);
        } catch (IOException e) {
            log.error("AddressService.zipCodeFounder() -> error IOException {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void save(Address address) throws JsonProcessingException {
        log.info("AddressService.save() -> init process, address {}", this.objectMapper.writeValueAsString(address));

        this.addressRepository.save(address);
    }

    public List<AddressDTO> findAllAddresses() throws JsonProcessingException {
        log.info("AddressService.findAllAddresses() -> init process");

        List<AddressDTO> addressDTOList = addressRepository.findAll().stream().map(AddressUtils::convertAddressToAddressDTO).toList();

        log.info("AddressService.findAllAddresses() -> finish process, addressList {}", objectMapper.writeValueAsString(addressDTOList));

        return addressDTOList;
    }

    public AddressDTO updateAddress(UUID addressUuid, String newZipCode, String newHouseNumber) throws JsonProcessingException {

        log.info("AddressService.updateAddress() -> init process, addressUuid {}, newZipCode {}, newHouseNumber {}", addressUuid, newZipCode, newHouseNumber);

        Address address = addressRepository.findById(addressUuid).orElseThrow(() -> new AddressNotFoundException(ADDRESS_NOT_FOUND_MESSAGE));

        Address updatedAddress = this.zipCodeFounder(newZipCode, newHouseNumber);

        address.setZipCode(updatedAddress.getZipCode());
        address.setHouseNumber(updatedAddress.getHouseNumber());
        address.setUf(updatedAddress.getUf());
        address.setStreetName(updatedAddress.getStreetName());
        address.setCity(updatedAddress.getCity());

        this.save(address);

        log.info("AddressService.updateAddress() -> finish process, addressUuid {}, updatedAddress {}", addressUuid, this.objectMapper.writeValueAsString(address));

        return addressRepository.findById(addressUuid).map(AddressUtils::convertAddressToAddressDTO).orElse(null);
    }
}
