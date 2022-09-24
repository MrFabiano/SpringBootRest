package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.PedidoControllerOpenApi;
import com.algafoodapi.domain.dto.PedidoDTO;
import com.algafoodapi.domain.dto.assembler.PedidoDTOAssembler;
import com.algafoodapi.domain.dto.assembler.PedidoDTODisassembler;
import com.algafoodapi.domain.dto.filter.PedidoFilter;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.PedidoInput;
import com.algafoodapi.domain.model.Pedido;
import com.algafoodapi.domain.model.Usuario;
import com.algafoodapi.domain.repository.PedidoRepository;
import com.algafoodapi.domain.service.PedidoService;
import com.algafoodapi.infrastructure.spec.PageableTranslator;
import com.algafoodapi.infrastructure.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoDTOAssembler dtoAssembler;
    @Autowired
    private PedidoDTODisassembler dtoDisassembler;
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> listar(){
        return dtoAssembler.toCollectionDomainObjects(pedidoRepository.findAll());
    }

    @GetMapping("/filtro")
    public Page<PedidoDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
        //pageable = this.traduzirPageable(pageable);
        pageable = traduzirPageable(pageable);
        Page<Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

        List<PedidoDTO> pedidoDTOS = dtoAssembler.toCollectionDomainObjects(pedidoPage.getContent());

        Page<PedidoDTO> pagePedido = new PageImpl<>(pedidoDTOS, pageable, pedidoPage.getTotalElements());

      return pagePedido;
    }


    @GetMapping("/{uuidPedido}")
    public PedidoDTO buscarPorId(@PathVariable String uuidPedido){
        try{
        Pedido pedido = pedidoService.buscarOuFalhar(uuidPedido);
        return dtoAssembler.toModelDomainObject(pedido);
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO salvar(@RequestBody @Valid PedidoInput pedidoInput){
        try{
            Pedido pedido = dtoDisassembler.toModelDomainObject(pedidoInput);
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);
            pedido = pedidoService.emitir(pedido);

            return dtoAssembler.toModelDomainObject(pedido);

        }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO atualizar(@RequestBody @Valid PedidoInput pedidoInput){
        try{
            Pedido pedido = dtoDisassembler.toModelDomainObject(pedidoInput);
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);
            pedido = pedidoService.emitir(pedido);

            return dtoAssembler.toModelDomainObject(pedido);

        }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }


    private Pageable traduzirPageable(Pageable apiPageable){
              var mapeamento = Map.of(
                      "uuid", "uuid",
                      "subTotal", "subTotal",
                       "taxaFrete", "taxaFrete",
                      "restaurante.nome", "restaurante.nome",
                      "restaurante.id", "restaurante.id",
                      "nomeCliente", "cliente.nome",
                      "valorTotal", "valorTotal"

              );
                 return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
