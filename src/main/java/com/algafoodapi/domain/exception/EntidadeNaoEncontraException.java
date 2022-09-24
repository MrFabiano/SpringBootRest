
package com.algafoodapi.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontraException extends NegocioException{

    public EntidadeNaoEncontraException(String mensagem) {
        super(mensagem);
    }
}
