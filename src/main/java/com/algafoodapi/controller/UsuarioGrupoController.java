package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.UsuarioGrupoControllerOpenAPi;
import com.algafoodapi.domain.dto.GrupoDTO;
import com.algafoodapi.domain.dto.assembler.GrupoDTOAssembler;
import com.algafoodapi.domain.model.Usuario;
import com.algafoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenAPi {

    @Autowired
    private GrupoDTOAssembler dtoAssembler;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<GrupoDTO> listar(@PathVariable Long usuarioId){
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        return dtoAssembler.toCollectionModelSet(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.OK)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId){

        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }
}
