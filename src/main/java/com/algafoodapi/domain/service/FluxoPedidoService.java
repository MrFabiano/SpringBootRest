package com.algafoodapi.domain.service;

import com.algafoodapi.domain.model.Pedido;
import com.algafoodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

 @Autowired
 private PedidoService pedidoService;

 @Autowired
 private PedidoRepository pedidoRepository;

 @Transactional
 public void confirmar(String uuidPedido){
  Pedido pedido =  pedidoService.buscarOuFalhar(uuidPedido);
  pedido.confirmar();

  pedidoRepository.save(pedido);

 }

 @Transactional
 public void cancelar(String uuidPedido){
  Pedido pedido = pedidoService.buscarOuFalhar(uuidPedido);
  pedido.cancelado();

 }

 @Transactional
 public void entregue(String uuidPedido){
  Pedido pedido = pedidoService.buscarOuFalhar(uuidPedido);
  pedido.entregue();

 }
}


