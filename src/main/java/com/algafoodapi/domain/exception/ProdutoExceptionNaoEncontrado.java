package com.algafoodapi.domain.exception;

public class ProdutoExceptionNaoEncontrado extends EntidadeNaoEncontraException{


    public ProdutoExceptionNaoEncontrado(String mensagem) {
        super(mensagem);
    }

    public ProdutoExceptionNaoEncontrado(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de produto com código %d para um restaurante de codigo %d", produtoId, restauranteId));
    }

}


