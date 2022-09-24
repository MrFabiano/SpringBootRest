package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.input.GrupoInput;
import com.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toModel(GrupoInput grupoInput){
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToModel(GrupoInput grupoInput, Grupo grupo){
         modelMapper.map(grupoInput, grupo);
    }
}
