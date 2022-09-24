package com.algafoodapi.domain.dto;

import com.algafoodapi.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

    private Long id;
    private String nome;
    private Estado estado;

}
