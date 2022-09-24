package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.EstadoControllerOPenApi;
import com.algafoodapi.domain.dto.EstadoDTO;
import com.algafoodapi.domain.dto.assembler.EstadoDTOAssembler;
import com.algafoodapi.domain.dto.assembler.EstadoDTODisassembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.EstadoInput;
import com.algafoodapi.domain.model.Estado;
import com.algafoodapi.domain.repository.EstadoRepository;
import com.algafoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/estado", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOPenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoDTOAssembler dtoAssembler;

    @Autowired
    private EstadoDTODisassembler dtoDisassembler;

    @GetMapping
    public List<EstadoDTO> listar(){
        return dtoAssembler.toCollectionDomainObject(estadoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO salvar(@RequestBody @Valid EstadoInput estadoInput){
       try{
           Estado estado = dtoDisassembler.toDomainObject(estadoInput);
           return dtoAssembler.toDomainObject(cadastroEstadoService.salvar(estado));
       }catch(EntidadeNaoEncontraException e){
           throw new NegocioException(e.getMessage());
       }
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
         try{
        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);
        //BeanUtils.copyProperties(estadoInput, estadoAtual, "id");
        dtoDisassembler.copyDomainObject(estadoInput, estadoAtual);

        return dtoAssembler.toDomainObject(estadoRepository.save(estadoAtual));
        }catch(EntidadeNaoEncontraException e){
             throw new NegocioException(e.getMessage());
         }
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscarPorId(@PathVariable Long estadoId) {
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);

        BeanUtils.copyProperties(estado, estadoId, "id");
        EstadoDTO estadoDTO = dtoAssembler.toDomainObject(estado);

        return estadoDTO;
    }

    @DeleteMapping("/{estadoId}")
    public void delete(@PathVariable Long estadoId) {
       cadastroEstadoService.excluir(estadoId);
    }
}
