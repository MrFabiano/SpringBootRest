package com.algafoodapi.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSenhaDTO {

    private Long id;
    private String email;
    private String senha;
}
