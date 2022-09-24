package com.algafoodapi.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    private String uuid;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String dataCriacao = String.valueOf(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL));
    private String dataConfirmacao = String.valueOf((DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)));
    private String dataCancelamento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    private String dataEntrega = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    private EnderecoDTO enderecoEntrega;
    private String status;

    private FormaPagamentoDTO formaPagamento;

    private UsuarioDTOResumo cliente;

    private RestauranteDTOResumo restaurante;

    private List<ItemPedidoDTOResumo> itensPedido;
}