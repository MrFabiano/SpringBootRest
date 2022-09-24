package com.algafoodapi.config.storage;

import com.algafoodapi.domain.email.service.EnvioEmailService;
import com.algafoodapi.domain.model.Pedido;
import com.algafoodapi.domain.model.PedidoCanceladoEvent;
import com.algafoodapi.domain.model.PedidoConfirmadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event){

        Pedido pedido = event.getPedido();

        var mensagem = EnvioEmailService.Mensagem.builder()
         .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
         .corpo("pedido-confirmado.html")
         .variavel("pedido", pedido)
         .destinatario(pedido.getCliente().getEmail())
          .build();


    }

    @EventListener
    public void aoCanceladoPedido(PedidoCanceladoEvent event){

        Pedido pedido = event.getPedido();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido canfirmado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();


    }
}
