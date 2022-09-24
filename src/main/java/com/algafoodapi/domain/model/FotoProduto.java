package com.algafoodapi.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FotoProduto {

    @Id
    @Column(name = "produto_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

    public Long getRestauranteId(){
        if(getProduto() != null){
            return getProduto().getRestaurante().getId();
        } else
            return null;
    }
}
