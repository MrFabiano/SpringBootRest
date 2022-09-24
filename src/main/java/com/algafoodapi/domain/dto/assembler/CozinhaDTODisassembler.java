package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.input.CozinhaInput;
import com.algafoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;


    public Cozinha toModelDomainObject(CozinhaInput cozinhaInput){
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToModelDomainObjetc(Cozinha cozinha, CozinhaInput cozinhaInput){
        modelMapper.map(cozinha, cozinhaInput);
    }
}
