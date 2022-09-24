package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.FormaPagamentoControllerOpenApi;
import com.algafoodapi.domain.dto.FormaPagamentoDTO;
import com.algafoodapi.domain.dto.assembler.FormaPagamentoDTOAssembler;
import com.algafoodapi.domain.dto.assembler.FormaPagamentoDTODisassembler;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.FormaPagamentoInput;
import com.algafoodapi.domain.model.FormaPagamento;
import com.algafoodapi.domain.repository.FormaPagamentoRepository;
import com.algafoodapi.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/v1/forma-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private FormaPagamentoDTOAssembler dtoAssembler;
    @Autowired
    private FormaPagamentoDTODisassembler dtoDisassembler;

    //Implementando com Deep ETags
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request){
        //Desabilitando o cache
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoRepository.getDataAtualizacao();

        if(dataAtualizacao != null){
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if(request.checkNotModified(eTag)){
            return null;

        }

        List<FormaPagamentoDTO> formaPagamentoDTOS =  dtoAssembler.toCollectionDomainObject
                (formaPagamentoRepository.findAll());
        return ResponseEntity.ok()
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())//cachePrivate = pode ser amarzenada em apenas cches locais.
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())//cachePublic = pode ser armazenada em cache local e compartilhado
                .eTag(eTag)
                //.cacheControl(CacheControl.noCache()) //noCache = sempre a validação vai ser obrigatoria no cache, sempre esta em stayo
                //.cacheControl(CacheControl.noStore()) //noStore = torna a resposta nao castiavel, se voce quer desativar o cache para uma resposta
                .body(formaPagamentoDTOS);

    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscarFormaPagamento(@PathVariable Long formaPagamentoId, ServletWebRequest request){
        try{
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String etag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoRepository.getDataAtualizacao();

        if(dataAtualizacao != null){
            etag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if(request.checkNotModified(etag)){
            return null;
        }
           FormaPagamento formaPagamento =formaPagamentoService.buscarOuFalhar(formaPagamentoId);
            //BeanUtils.copyProperties(formaPagamento, formaPagamentoId, "id");
           FormaPagamentoDTO formaPagamentoDTO = dtoAssembler.toModel(formaPagamento);
           return ResponseEntity.ok()
                   .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                   .eTag(etag)
                   .body(formaPagamentoDTO);

        }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        try {
            FormaPagamento formaPagamento = dtoDisassembler.toModel(formaPagamentoInput);
            return dtoAssembler.toModel(formaPagamentoService.salvar(formaPagamento));

        } catch (EntidadeNaoEncontraException e) {
            throw new NegocioException(e.getMessage());
        }
    }
        @PutMapping("/{formaPagamentoId}")
        @ResponseStatus(HttpStatus.CREATED)
        public FormaPagamentoDTO atualizar(@PathVariable Long  formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput ){
            try{
                FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
                //BeanUtils.copyProperties(formaPagamentoId, formaPagamentoInput, "id");
                dtoDisassembler.tocopyToModelObject(formaPagamentoInput, formaPagamentoAtual);

                FormaPagamentoDTO formaPagamentoDTO =
                        dtoAssembler.toModel(formaPagamentoService.salvar(formaPagamentoAtual));
                return formaPagamentoDTO;

        }catch(EntidadeNaoEncontraException e){
                throw new NegocioException(e.getMessage());
            }
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId){
        formaPagamentoService.excluir(formaPagamentoId);

    }
}
