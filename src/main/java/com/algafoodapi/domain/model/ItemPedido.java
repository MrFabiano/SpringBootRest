package com.algafoodapi.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    public void calcularPrecoTotal(){
        BigDecimal precoUnitario = this.getPrecoUnitario();
        Integer quantidade = this.getQuantidade();

        if(precoUnitario == null){
            precoUnitario = BigDecimal.ZERO;
        }

        if(quantidade == null){
            quantidade = 0;
        }

        this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
    }
}
