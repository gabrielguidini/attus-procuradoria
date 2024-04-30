package com.attus.procuradoria.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ViaCepDTO {
    // getting out of the english pattern 'cause the via-cep api needs to be in portuguese
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

}
