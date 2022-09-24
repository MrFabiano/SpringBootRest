package com.algafoodapi.domain.exception.handlerexception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL) //quando for null, não vai exibir na tela
@Builder
@Getter
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;
    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 5)
    private String tipo;
    @ApiModelProperty(example = "Dados invalidos", position = 10)
    private String titulo;
    @ApiModelProperty(example = "Um ou mais campos estão invalidos, Faça o preenchimento novamente", position = 15)
    private String detalhe;
    @ApiModelProperty(example = "Um ou mais campos estão invalidos, Faça o preenchimento novamente", position = 20)
    private String userMessage;
    @ApiModelProperty(example = "2022-03-17 03:08:00", position = 25)
    private String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"));
    @ApiModelProperty(value = "Objetos ou campos que geraram o erro", position = 30)
    private List<Object> objects;



    @ApiModel("ObjetoProblema")
	@Builder
	@Getter
    public static class Object{
        @ApiModelProperty(example = "preco")
        private String name;
        @ApiModelProperty(example = "O preço é obrigatorio")
        private String userMessage;

    }
}

