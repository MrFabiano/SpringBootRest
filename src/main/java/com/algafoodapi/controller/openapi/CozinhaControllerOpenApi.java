package com.algafoodapi.controller.openapi;

import com.algafoodapi.domain.dto.CozinhaDTO;
import com.algafoodapi.domain.exception.handlerexception.Problem;
import com.algafoodapi.domain.input.CozinhaInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Cozinha")
public interface CozinhaControllerOpenApi {


    @ApiOperation("Lista as cozinhas com paginação")
    public Page<CozinhaDTO> listar(@ApiParam(value = "ID de uma cidade", example = "1")Pageable pageable);


    @ApiOperation("Cadastrar as cozinhas")
    public CozinhaDTO adicionar(@ApiParam(name = "Corpo", value = "Representação de uma nova cozinha", example = "1")
                                    @RequestBody @Valid CozinhaInput cozinhaInput);


    @ApiOperation("Atualizar as cozinhas")
    public CozinhaDTO atualizar(@ApiParam(value = "ID de uma cozinha", example = "1")
                                    @PathVariable Long cozinhaId,
                                @ApiParam(name = "corpo", value = "Representação de uma cozinha com novos dados")
    @RequestBody @Valid CozinhaInput cozinhaInput);


    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaDTO buscarPorId(@ApiParam(value = "ID da cozinha", example = "1") Long cozinhaId);

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
    @ApiOperation("Exclui as cozinhas por ID")
    public void delete(@ApiParam(value = "ID de uma cozinha", example = "1")
                           @PathVariable Long cozinhaId);

}
