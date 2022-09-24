package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.dto.CozinhaDTO;
import com.algafoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toModelDomainObjetc(Cozinha cozinha){
        return modelMapper.map(cozinha, CozinhaDTO.class);

    }

    public List<CozinhaDTO> toCollectionDomainObjetcs(List<Cozinha> cozinhas){
        return cozinhas.stream()
                .map(cozinha -> toModelDomainObjetc(cozinha))
                .collect(Collectors.toList());

    }
}
