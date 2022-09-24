package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.input.PedidoInput;
import com.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toModelDomainObject(PedidoInput pedidoInput){
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToModelDomainObject(Pedido pedido, PedidoInput pedidoInput){
         modelMapper.map(pedido, pedidoInput);
    }
}
