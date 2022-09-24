package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.UsuarioDTO;
import com.algafoodapi.domain.exception.handlerexception.Problem;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Usuario-Responsavel")
public interface UsuarioResponsavelControllerOpenApi {



    @ApiOperation("Listas os usuarios responsaveis associados a restaurantes")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public List<UsuarioDTO> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Busca um usuario responsavel pelo ID")
    public UsuarioDTO buscarPorId(Long usuarioId);


    @ApiOperation("Atualiza usuario responsavel pelo ID")
    public void atualizar( Long restauranteId,  Long usuarioId);

    @ApiOperation("Exclui usuario responsavel pelo ID")
    public void excluir( Long usuarioId, Long restauranteId);

}




