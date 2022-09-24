package com.algafoodapi.domain.input;

import com.algafoodapi.domain.model.Permissao;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupoInput {

    private String nome;
    private List<Permissao> permissoes;
}
