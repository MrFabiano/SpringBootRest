package com.algafoodapi.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "Estado")
@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    //@ApiModelProperty(example = "1")
    private Long id;
    //@ApiModelProperty(value = "São Paulo")0,
}