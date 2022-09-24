package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.UsuarioControllerOpenApi;
import com.algafoodapi.domain.dto.UsuarioDTO;
import com.algafoodapi.domain.dto.UsuarioSenhaDTO;
import com.algafoodapi.domain.dto.assembler.UsuarioDTOAssembler;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.SenhaInput;
import com.algafoodapi.domain.input.UsuarioComSenhaInput;
import com.algafoodapi.domain.model.Usuario;
import com.algafoodapi.domain.repository.UsuarioRepository;
import com.algafoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDTOAssembler dtoAssembler;

    @GetMapping
    public List<UsuarioDTO> listar(){
        return dtoAssembler.toCollectionDomainObjects(usuarioRepository.findAll());

    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscarPorId(@PathVariable Long usuarioId){
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        return dtoAssembler.toModelDomainObject(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioSenhaDTO salvarUsuario(@RequestBody @Valid UsuarioComSenhaInput usuarioInput){
        Usuario usuario = dtoAssembler.toModelDomainObjectSenha(usuarioInput);
        usuario = usuarioService.salvar(usuario);

        return dtoAssembler.toObjectSenha(usuario);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.OK)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput){
        try{
        usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }catch(RuntimeException e){
            throw new NegocioException("Error created senha");
       }
    }
}
