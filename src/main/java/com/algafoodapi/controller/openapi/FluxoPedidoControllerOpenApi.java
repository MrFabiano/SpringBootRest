package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.exception.handlerexception.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Fluxo-Pedido")
public interface FluxoPedidoControllerOpenApi {


    @ApiOperation("Confirmação do pedido")
    @ApiResponses({@ApiResponse(
            code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)

    })
    public void confirmar(String uuidPedido);


    @ApiOperation("Cancelamento do pedido")
    @ApiResponses({@ApiResponse(
            code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)

    })
    public void cancelar(@PathVariable String uuidPedido);


    @ApiOperation("Pedido entregue")
    @ApiResponses({@ApiResponse(
            code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)

    })
    public void entregue(String uuidPedido);

}