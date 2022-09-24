package com.algafoodapi.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class RestauranteDTO {

    private Long id;
    private String nome;
    private String dataCadastro = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"));
    private String dataAtualizacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    private BigDecimal taxaFrete;
    private  CozinhaDTO cozinha;
    private Boolean ativo;
    private EnderecoDTO endereco;
}
