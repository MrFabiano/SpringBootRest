package com.algafoodapi.domain.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdInput {

    //@ApiModelProperty(example = "São Paulo")
    private Long id;
    private String nome;
    private EstadoInput estado;
}
