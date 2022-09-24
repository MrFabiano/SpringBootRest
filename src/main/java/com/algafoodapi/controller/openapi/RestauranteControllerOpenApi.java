package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.RestauranteDTO;
import com.algafoodapi.domain.exception.handlerexception.Problem;
import com.algafoodapi.domain.input.RestauranteInput;
import com.algafoodapi.domain.model.Restaurante;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {


    @ApiOperation("Lista os restaurantes")
    public List<RestauranteDTO> listar();


    @ApiOperation(value = "Lista restaurantes", hidden = true)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeçao de pedidos", allowableValues = "apenas-nome", name = "projeao",
                    paramType = "query", type = "string")
    })
    public List<RestauranteDTO> listarApenasNome();

    //  return dtoAssembler.toCollectionDTO(restauranteRepository.findAll());
    //@JsonView(RestauranteView.class)
    //@CrossOrigin


    @ApiOperation(value = "Lista restaurantes", hidden = true)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeçao de pedidos", allowableValues = "apenas-nome", name = "projeao",
                    paramType = "query", type = "string")
    })

    public ResponseEntity<List<RestauranteDTO>> listarRestaurantes();

    //
//        return ResponseEntity.ok()
//                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "localhost:7070/restaurantes", "*")
//                .body(restauranteDTOS);
    @ApiOperation("Busca uma Restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteDTO buscarPorId( @ApiParam(value = "ID restaurante", example = "1", required = true)Long restauranteId);

    @ApiOperation("Cadastra um novo restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma restaurante", required = true) RestauranteInput restauranteInput);

    @ApiOperation("Atualiza restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID Restaurane inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteDTO atualizar(@ApiParam(value = "ID de uma restaurante", example = "1", required = true)  Long restauranteId,
                                    RestauranteInput restauranteInput);

    public RestauranteDTO atualizarParcial(Long restauranteId, RestauranteInput restauranteInput,
                                           Map<String, Object> campos, HttpServletRequest request);


    private void validator(Restaurante restauranteAtual, String objectName) throws ValidationException {

    }
}