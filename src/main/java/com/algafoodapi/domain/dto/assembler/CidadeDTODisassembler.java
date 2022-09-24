package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.input.CidadeInput;
import com.algafoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInput cidadeIdInput){
        return modelMapper.map(cidadeIdInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeIdInput, Cidade cidade){
        //cidade.setEstado(new Estado());
        modelMapper.map(cidadeIdInput, cidade);
    }
}
