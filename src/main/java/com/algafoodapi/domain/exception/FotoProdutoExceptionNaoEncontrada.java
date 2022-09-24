package com.algafoodapi.domain.exception;

public class FotoProdutoExceptionNaoEncontrada extends EntidadeNaoEncontraException {


    public FotoProdutoExceptionNaoEncontrada(Long restauranteId, Long produtoId) {
        this(String.format("Não foi possivel recuperar o arquivo %d da foto %d", restauranteId, produtoId));
    }

    public FotoProdutoExceptionNaoEncontrada(String mensagem) {
        super(mensagem);
    }
}

