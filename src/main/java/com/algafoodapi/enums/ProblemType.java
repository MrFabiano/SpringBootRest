package com.algafoodapi.enums;


import lombok.Getter;

@Getter
public enum ProblemType {

    DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos"),
    ERRO_SISTEMA("/erro-do-sistema", "Erro do Sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro Invalido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem Incompreensivel"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso Não Encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade Em Uso"),
    ENTIDADE_NAO_PERMITIDA("/entidade-nao-permitida", "Entidade Não Permitida");

    private String titulo;
    private String uri;

    ProblemType(String path, String titulo){
        this.titulo = titulo;
        this.uri = "https://algafoog.com.br" + path;
    }
}
