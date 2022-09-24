package com.algafoodapi.domain.exception;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends NegocioException {

    public EntidadeEmUsoException(String mensagem){
        super(mensagem);
    }
}
