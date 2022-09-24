package com.algafoodapi.domain.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

  @NotNull
  private RestauranteIdInput restaurante;
  @NotNull
  private FormaPagamentoInput formaPagamento;
  private EnderecoInput enderecoEntrega;
  @Valid
  @Size(min = 1)
  @NotNull
  private List<ItemPedidoInput> itensPedido;

}
