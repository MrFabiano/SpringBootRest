package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.PedidoDTO;
import com.algafoodapi.domain.dto.filter.PedidoFilter;
import com.algafoodapi.domain.exception.handlerexception.Problem;
import com.algafoodapi.domain.input.PedidoInput;
import com.algafoodapi.infrastructure.spec.PageableTranslator;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Api(tags = "Pedido")
public interface PedidoControllerOpenApi {


    public List<PedidoDTO> listar();


    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes separados por virgula",
                    name = "campos", paramType =  "query", type = "string")
    })
    @ApiOperation("Lista os pedidos")
    public Page<PedidoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes separados por virgula",
                    name = "campos", paramType =  "query", type = "string")
    })

    @ApiOperation("Busca os pedidos por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID pedido inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public PedidoDTO buscarPorId(@ApiParam(value = "ID pedido", example = "1") String uuidPedido);

    @ApiOperation("Cadastra os novos pedidos")
    public PedidoDTO salvar( @ApiParam(name = "corpo", value = "Representação de um pedido")  PedidoInput pedidoInput);

    @ApiOperation("Atualiza os novos pedidos")
    public PedidoDTO atualizar(@ApiParam(value = "ID Pedido", example = "1")  PedidoInput pedidoInput);

    private Pageable traduzirPageable(Pageable apiPageable){
        var mapeamento = Map.of(
                "uuid", "uuid",
                "subTotal", "subTotal",
                "taxaFrete", "taxaFrete",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"

        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
