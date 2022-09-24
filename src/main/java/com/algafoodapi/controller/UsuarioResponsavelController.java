package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.UsuarioResponsavelControllerOpenApi;
import com.algafoodapi.domain.dto.UsuarioDTO;
import com.algafoodapi.domain.dto.assembler.UsuarioDTOAssembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.UsuarioDTOInput;
import com.algafoodapi.domain.model.Restaurante;
import com.algafoodapi.domain.model.Usuario;
import com.algafoodapi.domain.service.CadastroRestauranteService;
import com.algafoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResponsavelController implements UsuarioResponsavelControllerOpenApi {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private UsuarioDTOAssembler dtoAssembler;

    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return dtoAssembler.toCollectionDomainObjectSet(restaurante.getResponsaveis());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscarPorId(@PathVariable Long usuarioId){
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        return dtoAssembler.toModelDomainObject(usuario);
    }

    @PostMapping
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioDTOInput usuarioInput){
        try{
        Usuario usuario = dtoAssembler.toModelDomainObjectInput(usuarioInput);
        return dtoAssembler.toModelDomainObject(usuarioService.salvar(usuario));

    }catch (EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizar( @PathVariable Long restauranteId, @PathVariable Long usuarioId){
        //Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        //Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        cadastroRestauranteService.associarResponsavel(restauranteId,usuarioId);

    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long usuarioId, @PathVariable Long restauranteId){
        cadastroRestauranteService.desassociarResponsavel(usuarioId,restauranteId);
    }
}
