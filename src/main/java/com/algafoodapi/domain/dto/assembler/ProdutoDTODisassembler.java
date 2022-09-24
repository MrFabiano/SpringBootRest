package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.input.ProdutoInput;
import com.algafoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toModelDomainObject(ProdutoInput produtoInput){
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToModelCollectionObject(Produto produto, ProdutoInput produtoInput){
        modelMapper.map(produto, produtoInput);
    }
}
