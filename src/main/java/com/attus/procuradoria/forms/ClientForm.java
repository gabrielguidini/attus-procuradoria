package com.attus.procuradoria.forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientForm {

    private String name;
    private String surname;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
