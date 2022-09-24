package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.RestauranteControllerOpenApi;
import com.algafoodapi.domain.dto.RestauranteDTO;
import com.algafoodapi.domain.dto.assembler.RestauranteDTOAssembler;
import com.algafoodapi.domain.dto.assembler.RestauranteDTODisassembler;
import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.input.RestauranteInput;
import com.algafoodapi.domain.model.Restaurante;
import com.algafoodapi.domain.model.view.RestauranteView;
import com.algafoodapi.domain.repository.RestauranteRepository;
import com.algafoodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping(value = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteDTOAssembler dtoAssembler;

    @Autowired
    private RestauranteDTODisassembler dtoDisassembler;


    @GetMapping
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteDTO> listar() {
        return dtoAssembler.toCollectionDTO(restauranteRepository.findAll());
    }

    @GetMapping(params = "projecao=apenas-nome")
    @JsonView(RestauranteView.ApenasNome.class)
    public List<RestauranteDTO> listarApenasNome() {
      //  return dtoAssembler.toCollectionDTO(restauranteRepository.findAll());
        return listar();
    }
    @GetMapping("/restaurantes")
    public ResponseEntity<List<RestauranteDTO>> listarRestaurantes() {
        List<RestauranteDTO> restauranteDTOS = dtoAssembler.toCollectionDTO(restauranteRepository.findAll());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(restauranteDTOS);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "localhost:7070/restaurantes", "*")
//                .body(restauranteDTOS);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscarPorId(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        BeanUtils.copyProperties(restaurante, restauranteId, "id");

        RestauranteDTO dto = dtoAssembler.toModel(restaurante);

        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
        try {
            Restaurante restaurante = dtoDisassembler.toDomainObject(restauranteInput);
            return dtoAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
            //return new ResponseEntity<Restaurante>(restaurante, HttpStatus.CREATED);
        }catch (EntidadeNaoEncontraException | EntidadeEmUsoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                                 @RequestBody @Valid RestauranteInput restauranteInput){
        try {
       // Restaurante restaurante = dtoDesassembler.toDomainObject(restauranteInput);

        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        dtoDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        //BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos",
                    //"endereco", "dataCadastro", "dataAtualizacao", "produtos");
            return dtoAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
        }catch (EntidadeNaoEncontraException | EntidadeEmUsoException e){
            throw new NegocioException(e.getMessage());
        }
    }
    @PatchMapping("/{restauranteId}")
    public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId, RestauranteInput restauranteInput,
                                        @RequestBody Map<String, Object> campos, HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        dtoDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        merge(campos, restauranteAtual, request);
        validator(restauranteAtual, "restaurante");

        return atualizar(restauranteId, restauranteInput);
    }

    private void validator(Restaurante restauranteAtual, String objectName) throws ValidationException {

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteAtual, objectName);

        validator.validate(restauranteAtual, bindingResult);

        if(bindingResult.hasErrors());
            throw new ValidationException(String.valueOf(bindingResult));
        }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                       HttpServletRequest request){
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);


        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            System.out.println(restauranteOrigem);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->{
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

              Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            //System.out.println(nomePropriedade+ "  " +valorPropriedade);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);

        });
   }catch(IllegalArgumentException e){
         Throwable rootCause = ExceptionUtils.getRootCause(e);
         throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){
        cadastroRestauranteService.ativar(restauranteId);

    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId){
        cadastroRestauranteService.inativar(restauranteId);

    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.OK)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try{
        cadastroRestauranteService.ativar(restaurantesIds);
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try{
        cadastroRestauranteService.inativar(restaurantesIds);
    }catch(EntidadeNaoEncontraException e){
            throw new NegocioException(e.getMessage());
        }
    }
}
