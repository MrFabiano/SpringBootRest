package com.algafoodapi.domain.dto;

import com.algafoodapi.domain.model.Permissao;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupoDTO {

    private Long id;
    private String nome;
    private List<Permissao> permissoes;
}
