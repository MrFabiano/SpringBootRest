package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.FormaPagamentoDTO;
import com.algafoodapi.domain.exception.handlerexception.Problem;
import com.algafoodapi.domain.input.FormaPagamentoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Forma-Pagamento")
public interface FormaPagamentoControllerOpenApi {


    @ApiOperation("Listas as formas de pagamento")
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request);


    @ApiOperation("Busca uma Forma-Pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID forma-pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma-Pagamento não encontrada", response = Problem.class)
    })
    public ResponseEntity<FormaPagamentoDTO> buscarFormaPagamento(@ApiParam(value = "ID de uma forma-pagamento", example = "1") Long formaPagamentoId, ServletWebRequest request);



    @ApiOperation("Cadatra uma forma-pagamento")
    public FormaPagamentoDTO salvar(@ApiParam(name = "corpo", value = "Representação de uma forma-pagamento") FormaPagamentoInput formaPagamentoInput);


    @ApiOperation("Atualiza forma-pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma-pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma-Pagamento não encontrada", response = Problem.class)
    })
    public FormaPagamentoDTO atualizar(@ApiParam(value = "ID de uma forma-pagamento", example = "1") Long  formaPagamentoId, FormaPagamentoInput formaPagamentoInput );

     @ApiOperation("Exclui as formas de pagamento por ID")
    public void excluir(Long formaPagamentoId);

    }


