package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.dto.ProdutoDTO;
import com.algafoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO toModelDomainObject(Produto produto){
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionDomainObjetcs(List<Produto> produtos){
      return produtos.stream()
                 .map(produto -> toModelDomainObject(produto))
                .collect(Collectors.toList());

    }
}
