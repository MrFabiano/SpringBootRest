package com.algafoodapi.config.openapi;

import com.algafoodapi.domain.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi {

    private List<CozinhaDTO> content;
    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private Long size;
    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;
    @ApiModelProperty(example = "5", value = "Total de páginas")
    private Long totalPages;
    @ApiModelProperty(example = "0", value = "Número da página")
    private Long number;
}
