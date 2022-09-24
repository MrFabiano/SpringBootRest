package com.algafoodapi.domain.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UsuarioDTOInput {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String dataCadastro = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
}
