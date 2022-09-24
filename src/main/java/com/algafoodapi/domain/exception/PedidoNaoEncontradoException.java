package com.algafoodapi.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontraException{

	private static final long serialVersionUID = 1L;


    public PedidoNaoEncontradoException(String uuidPedido) {
        super(String.format("Não existe um pedido com código %s", uuidPedido));
    }

}
