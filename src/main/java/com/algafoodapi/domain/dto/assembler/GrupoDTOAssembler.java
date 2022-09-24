package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.dto.GrupoDTO;
import com.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GrupoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO toModel(Grupo grupo){
        return modelMapper.map(grupo, GrupoDTO.class);
    }

   public List<GrupoDTO> toCollectionModel(List<Grupo> grupos){
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
   }

    public List<GrupoDTO> toCollectionModelSet(Set<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }
}
