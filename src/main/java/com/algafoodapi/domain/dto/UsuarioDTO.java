package com.algafoodapi.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    //@DateTimeFormat(pattern = "dd/MM/yyyy  HH:mm:ss")
    private String dataCadastro = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    //private String dataCadastro = String.valueOf(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL));
    private GrupoDTO grupo;
}
