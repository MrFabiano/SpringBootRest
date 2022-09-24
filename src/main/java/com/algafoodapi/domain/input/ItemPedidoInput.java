package com.algafoodapi.domain.input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoInput {

    private Long produtoId;
    private Integer quantidade;
    private String observacao;
}
