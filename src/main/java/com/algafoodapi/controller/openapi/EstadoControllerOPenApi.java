package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.EstadoDTO;
import com.algafoodapi.domain.exception.handlerexception.Problem;
import com.algafoodapi.domain.input.EstadoInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Estado")
public interface EstadoControllerOPenApi {


    @ApiOperation("Listas os estados")
    public List<EstadoDTO> listar();

    @ApiOperation("Cadatra os estados")
    public EstadoDTO salvar(@ApiParam(name = "corpo", value = "Representação de uma forma-pagamento") EstadoInput estadoInput);


    @ApiOperation("Atualiza os Estados por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrada", response = Problem.class)
    })
    public EstadoDTO atualizar(Long estadoId, EstadoInput estadoInput);

    @ApiOperation("Busca Estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrada", response = Problem.class)
    })
    public EstadoDTO buscarPorId(Long estadoId);

    @ApiOperation("Exclui estado por ID")
    public void delete(Long estadoId);
}

