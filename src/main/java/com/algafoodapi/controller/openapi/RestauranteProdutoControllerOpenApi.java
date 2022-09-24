package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.ProdutoDTO;
import com.algafoodapi.domain.input.ProdutoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "Produto")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Listas os produtos")
    public List<ProdutoDTO> listar();

    @ApiOperation("Busca os produtos por ID")
    public ProdutoDTO buscaPorId(Long produtoId, Long restauranteId);


    @ApiOperation("Cadatra os produtos")
    public ProdutoDTO salvar(Long restauranteId, ProdutoInput produtoInput);

}
