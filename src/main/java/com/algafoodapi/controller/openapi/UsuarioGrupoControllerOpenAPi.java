package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.GrupoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "Usuario-Grupo")
public interface UsuarioGrupoControllerOpenAPi {


    @ApiOperation("Lista os usuarios do grupo")
    public List<GrupoDTO> listar( Long usuarioId);


    @ApiOperation("Associa um usuario ao grupo")
    public void associar(Long usuarioId, Long grupoId);


    @ApiOperation("Desassocia um usuario do grupo")
    public void desassociar(Long usuarioId, Long grupoId);

}
