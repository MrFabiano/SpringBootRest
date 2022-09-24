package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.GrupoDTO;
import com.algafoodapi.domain.input.GrupoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Grupo")
public interface GrupoControllerOpenApi {


    @ApiOperation("Lista os Grupos")
    public List<GrupoDTO> listar();


    @ApiOperation("Lista os grupos")
    public GrupoDTO buscarPorId(@ApiParam(value = "ID de um grupo", example = "1")
                                    @PathVariable Long grupoId);


    @ApiOperation("Cadastra os grupos")
    public GrupoDTO salvar(@ApiParam(name = "Corpo", value = "Representação de um grupo", example = "1")
                               @RequestBody @Valid GrupoInput grupoInput);


    @ApiOperation("Atualiz os grupos")
    public GrupoDTO atualizar(@ApiParam(name = "Corpo", value = "ID de um grupo", example = "1")
                                  @PathVariable Long grupoId,
                              @ApiParam(name = "corpo", value = "Representação de um grupo com novos dados")
    @RequestBody GrupoInput grupoInput);


    @ApiOperation("Exclua grupo por ID")
    public void excluir(@ApiParam(value = "ID de uma cidade", example = "1")
                            @PathVariable Long grupoId);


}
