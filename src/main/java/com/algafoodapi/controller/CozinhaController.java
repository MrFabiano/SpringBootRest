package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.CozinhaControllerOpenApi;
import com.algafoodapi.domain.dto.CozinhaDTO;
import com.algafoodapi.domain.dto.assembler.CozinhaDTOAssembler;
import com.algafoodapi.domain.dto.assembler.CozinhaDTODisassembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.CozinhaInput;
import com.algafoodapi.domain.model.Cozinha;
import com.algafoodapi.domain.repository.CozinhaRepository;
import com.algafoodapi.domain.service.CadastroCozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaDTOAssembler dtoAssembler;

    @Autowired
    private CozinhaDTODisassembler dtoDisassembler;

    @GetMapping
    public Page<CozinhaDTO> listar(Pageable pageable) {
        log.info("Consultando cozinhas", pageable.getPageSize());

        Page<Cozinha> cozinhaPage = cozinhaRepository.findAll(pageable);

        List<CozinhaDTO> cozinhaDTOS = dtoAssembler.toCollectionDomainObjetcs(cozinhaPage.getContent());

        Page<CozinhaDTO> cozinhaDTOList = new PageImpl<>(cozinhaDTOS, pageable, cozinhaPage.getTotalElements());

        return  cozinhaDTOList;
    }

    @PostMapping
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        log.error("Não encontrada");
        try{
        Cozinha cozinha = dtoDisassembler.toModelDomainObject(cozinhaInput);

        return dtoAssembler.toModelDomainObjetc(cadastroCozinhaService.salvar(cozinha));
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        try{
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        BeanUtils.copyProperties(cozinhaInput, cozinhaAtual, "id");

        return dtoAssembler.toModelDomainObjetc(cadastroCozinhaService.salvar(cozinhaAtual));
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }
    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscarPorId(@PathVariable Long cozinhaId) {
        try{
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhaId, "id");

        CozinhaDTO cozinhaDTO = dtoAssembler.toModelDomainObjetc(cozinha);

        return cozinhaDTO;
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }
    /* @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> delete(@PathVariable Long cozinhaId) {
        try {
            cadastroCozinhaService.excluir(cozinhaId);
            return new ResponseEntity<Cozinha>(HttpStatus.NO_CONTENT);
        }catch(EntidadeNaoEncontraException e){
            return new ResponseEntity<Cozinha>(HttpStatus.NOT_FOUND);
        } catch (EntidadeEmUsoException e) {
            return new ResponseEntity<Cozinha>(HttpStatus.CONFLICT);
        }
    }
*/
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void delete(@PathVariable Long cozinhaId) {
            cadastroCozinhaService.excluir(cozinhaId);
    }
}

