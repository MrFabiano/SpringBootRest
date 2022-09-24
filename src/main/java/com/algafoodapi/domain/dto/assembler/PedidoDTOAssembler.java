package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.dto.PedidoDTO;
import com.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO toModelDomainObject(Pedido pedido){
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollectionDomainObjects(List<Pedido> pedidos){
        return pedidos.stream()
                .map(pedido -> toModelDomainObject(pedido))
                .collect(Collectors.toList());
    }
}
