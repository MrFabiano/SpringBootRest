package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.FotoProdutoDTO;
import com.algafoodapi.domain.input.FotoProdutoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Foto-Produto")
public interface RestauranteFotoProdutoControllerOPenApi {



    @ApiOperation("Atualiza a foto do produto")
    public FotoProdutoDTO atualizarFoto( Long restauranteId,
                                        Long produtoId,  FotoProdutoInput fotoProdutoInput, @ApiParam(value = "Arquivo da foto", required = true) MultipartFile arquivo) throws IOException;



    @ApiOperation("Busca a foto do produto por ID")
    public FotoProdutoDTO buscarFoto(Long restauranteId,  Long produtoId);


    @ApiOperation("Busca a foto do produto por imagem")
    public ResponseEntity<?> buscarFotoImagem(Long restauranteId,
                                               Long produtoId,
                                              @RequestHeader(name = "accept")String acceptHeader);


    @ApiOperation("Exclui a foto do produto por ID")
    public void removerFoto(Long restauranteId,  Long produtoId);

}