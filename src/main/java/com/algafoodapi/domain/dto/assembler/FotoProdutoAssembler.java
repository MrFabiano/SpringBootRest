package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.dto.FotoProdutoDTO;
import com.algafoodapi.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toModelDomainObject(FotoProduto fotoProduto) {

        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }
}
