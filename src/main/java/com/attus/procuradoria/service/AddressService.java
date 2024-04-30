package com.attus.procuradoria.service;

import com.attus.procuradoria.dto.AddressDTO;
import com.attus.procuradoria.dto.ViaCepDTO;
import com.attus.procuradoria.entity.Address;
import com.attus.procuradoria.exceptions.ViaCepException;
import com.attus.procuradoria.repository.AddressRepository;
import com.attus.procuradoria.utils.AddressUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressDTO zipCodeFounder(String commingZipCode, String houseNumber) {

        log.info("");

        ViaCepDTO viaCep = null;

        HttpGet request = new HttpGet("https://viacep.com.br/ws/"+ commingZipCode +"/json/");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();
                viaCep = gson.fromJson(result, ViaCepDTO.class);
            }
            if (viaCep == null) {
                log.error("AddressService.zipCodeFounder() -> Zip code not found, zipCode {}", commingZipCode);
                throw new ViaCepException("Zip Code Info is Empty");
            }
            viaCep.setComplemento(houseNumber);

            Address updatedAddress = AddressUtils.convertViaCepDTOtoAddress(viaCep);

            this.addressRepository.save(updatedAddress);

            log.info("");

            return AddressUtils.convertAddressToAddressDTO(updatedAddress);

        } catch (ViaCepException e) {
            log.error("AddressService.zipCodeFounder() -> error while trying to retrieve zip code info, zipCode {} ,error {}", commingZipCode, e.getMessage());
            throw new ViaCepException(e.getMessage());
        } catch (IOException e) {
            log.error("AddressService.zipCodeFounder() -> error IOException {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
