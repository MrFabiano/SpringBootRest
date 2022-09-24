package com.algafoodapi.domain.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class FormaPagamentoInput {

    private Long id;
    @NotBlank
    private String descricao;

}
