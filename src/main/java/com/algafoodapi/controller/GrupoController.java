package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.GrupoControllerOpenApi;
import com.algafoodapi.domain.dto.GrupoDTO;
import com.algafoodapi.domain.dto.assembler.GrupoDTOAssembler;
import com.algafoodapi.domain.dto.assembler.GrupoDTODisassembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.GrupoInput;
import com.algafoodapi.domain.model.Grupo;
import com.algafoodapi.domain.repository.GrupoRepository;
import com.algafoodapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/grupo", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private GrupoDTOAssembler dtoAssembler;
    @Autowired
    private GrupoDTODisassembler dtoDisassembler;

    @GetMapping
    public List<GrupoDTO> listar(){
        return dtoAssembler.toCollectionModel(grupoRepository.findAll());

    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscarPorId(@PathVariable Long grupoId){
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        return dtoAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInput grupoInput){
        try{
            Grupo grupo = dtoDisassembler.toModel(grupoInput);
            return dtoAssembler.toModel(grupoService.salvar(grupo));
        }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());

        }
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody GrupoInput grupoInput){
        try{
            Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);
            dtoDisassembler.copyToModel(grupoInput,grupoAtual);

            GrupoDTO grupoDTO = dtoAssembler.toModel(grupoService.salvar(grupoAtual));
                    return grupoDTO;
        }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());

        }
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long grupoId){
        grupoService.excluir(grupoId);
    }
}
