package com.algafoodapi.controller;

import com.algafoodapi.controller.openapi.FluxoPedidoControllerOpenApi;
import com.algafoodapi.domain.email.EmailExecption;
import com.algafoodapi.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/pedidos/{uuidPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.OK)
    public void confirmar(@PathVariable String uuidPedido){
        try{
        fluxoPedidoService.confirmar(uuidPedido);
    }catch (Exception e){
       throw new EmailExecption("Não foi possivel mandar o e-mail", e);
        }
    }

    @PutMapping("/cancelado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String uuidPedido){
        fluxoPedidoService.cancelar(uuidPedido);
    }

    @PutMapping("/entregue")
    @ResponseStatus(HttpStatus.OK)
    public void entregue(@PathVariable String uuidPedido) {
        fluxoPedidoService.entregue(uuidPedido);
    }
}
