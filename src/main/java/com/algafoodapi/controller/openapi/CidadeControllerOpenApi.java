package com.algafoodapi.controller.openapi;


import com.algafoodapi.domain.dto.CidadeDTO;
import com.algafoodapi.domain.input.CidadeInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeDTO> listar();

    @ApiOperation("Buscar as cidades por ID")
    public CidadeDTO buscarPorId(@ApiParam(value = "ID de uma cidade", example = "1")
                                         Long cidadeId);

    @ApiOperation("Cadastra as cidades")
    public CidadeDTO adicionar(@ApiParam(name = "Corpo", value = "Representação de uma nova cidade", example = "1")
                                           CidadeInput cidadeIdInput);

    @ApiOperation("Atualiza as cidades po ID")
    public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade", example = "1")
                                       Long cidadeId,
                               @ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados")
                                       CidadeInput cidadeIdInput);

    @ApiOperation("Exclua as cidades por ID")
    public void delete(@ApiParam(value = "ID de uma cidade", example = "1")
                               Long cidadeId);


}


