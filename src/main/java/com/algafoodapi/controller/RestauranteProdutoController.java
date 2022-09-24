package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.RestauranteProdutoControllerOpenApi;
import com.algafoodapi.domain.dto.ProdutoDTO;
import com.algafoodapi.domain.dto.assembler.ProdutoDTOAssembler;
import com.algafoodapi.domain.dto.assembler.ProdutoDTODisassembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.ProdutoInput;
import com.algafoodapi.domain.model.Produto;
import com.algafoodapi.domain.model.Restaurante;
import com.algafoodapi.domain.repository.ProdutoRepository;
import com.algafoodapi.domain.service.CadastroRestauranteService;
import com.algafoodapi.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private ProdutoDTOAssembler dtoAssembler;
    @Autowired
    private ProdutoDTODisassembler dtoDisassembler;
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<ProdutoDTO> listar(){
        try{
            return dtoAssembler.toCollectionDomainObjetcs(produtoRepository.findAll());
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscaPorId(@PathVariable Long produtoId, @PathVariable Long restauranteId){
        try{
        Produto produto = produtoService.buscarOuFalhar(produtoId, restauranteId);
        return dtoAssembler.toModelDomainObject(produto);
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PostMapping
    public ProdutoDTO salvar(@PathVariable Long restauranteId,  @RequestBody @Valid ProdutoInput produtoInput){
        try{
            Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
            Produto produto = dtoDisassembler.toModelDomainObject(produtoInput);
            produto.setRestaurante(restaurante);
            //produto = produtoService.salvar(produto);
            return dtoAssembler.toModelDomainObject(produtoService.salvar(produto));

    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }
}
