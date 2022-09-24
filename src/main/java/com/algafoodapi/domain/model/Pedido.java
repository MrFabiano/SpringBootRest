package com.algafoodapi.domain.model;

import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.enums.StatusPedido;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    @CreationTimestamp
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    @Embedded
    private Endereco enderecoEntrega;
    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;
    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;
    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public void calcularValorTotal() {
        getItensPedido().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItensPedido().stream()
                .map(itemPedido -> itemPedido.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {
        setTaxaFrete(getRestaurante().getTaxaFrete());
    }

    public void atribuirPedidoAosItens() {
        getItensPedido().forEach(itemPedido -> itemPedido.setPedido(this));
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void cancelado() {
        setStatus(StatusPedido.CANCELADO);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoCanceladoEvent(this));
    }

    public void entregue() {
        setStatus(StatusPedido.ENTREGUE);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void setStatus(StatusPedido novoStatus) {
        if(!getStatus().naoPodeAlterar(novoStatus)){
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            getUuid(), getStatus().getDescricao(),
                    novoStatus.getDescricao()));
        }
        this.status = novoStatus;
       }

       @PrePersist
       private void gerarUUID(){
        setUuid(UUID.randomUUID().toString());
       }
    }

