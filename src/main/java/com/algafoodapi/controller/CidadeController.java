package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.CidadeControllerOpenApi;
import com.algafoodapi.controller.urihelper.ResourceUriHelper;
import com.algafoodapi.domain.dto.CidadeDTO;
import com.algafoodapi.domain.dto.assembler.CidadeDTOAssembler;
import com.algafoodapi.domain.dto.assembler.CidadeDTODisassembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.CidadeInput;
import com.algafoodapi.domain.model.Cidade;
import com.algafoodapi.domain.repository.CidadeRepository;
import com.algafoodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeDTOAssembler dtoAssembler;

    @Autowired
    private CidadeDTODisassembler dtoDisassembler;

    @Override
    @GetMapping
    public CollectionModel<CidadeDTO> listar() {

       return  dtoAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO buscarPorId(@PathVariable Long cidadeId){
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
        BeanUtils.copyProperties(cidade, cidadeId, "id");

        CidadeDTO cidadeDTO = dtoAssembler.toModel(cidade);

        return cidadeDTO;
    }
    @PostMapping
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeIdInput) {
        CidadeDTO cidadeDTO;
        try {
            Cidade cidade = dtoDisassembler.toDomainObject(cidadeIdInput);

            cidadeDTO = dtoAssembler.toModel(cadastroCidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());

        } catch (EntidadeNaoEncontraException e) {
            throw new NegocioException(e.getMessage());
        }
        return cidadeDTO;
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable Long cidadeId,
                               @RequestBody @Valid CidadeInput cidadeIdInput) {
        Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);
        //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        dtoAssembler.toModel(cidadeAtual);
        try{
        return dtoAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
        }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public void delete(@PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);

    }
}



