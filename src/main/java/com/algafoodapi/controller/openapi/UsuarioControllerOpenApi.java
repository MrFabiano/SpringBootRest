package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.UsuarioDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "Usuario")
public interface UsuarioControllerOpenApi {



    @ApiOperation("Listas os usuarios")
    public List<UsuarioDTO> listar();

//    @ApiOperation("Busca um usuario responsavel pelo ID")
//    public UsuarioDTO buscarPorId(Long usuarioId);
//
//
//    @ApiOperation("Atualiza usuario responsavel pelo ID")
//    public void atualizar( Long restauranteId,  Long usuarioId);
//
//    @ApiOperation("Exclui usuario responsavel pelo ID")
//    public void excluir( Long usuarioId, Long restauranteId);

}





