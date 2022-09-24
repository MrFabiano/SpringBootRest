package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.dto.UsuarioDTO;
import com.algafoodapi.domain.dto.UsuarioSenhaDTO;
import com.algafoodapi.domain.input.UsuarioDTOInput;
import com.algafoodapi.domain.input.UsuarioInput;
import com.algafoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOAssembler {

    @Autowired
    private ModelMapper   modelMapper;


    public UsuarioDTO toModelDomainObject(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public UsuarioSenhaDTO toObjectSenha(Usuario usuario){
        return modelMapper.map(usuario, UsuarioSenhaDTO.class);
    }

    public Usuario toModelDomainObjectInput(UsuarioDTOInput usuarioDTOInput){
        return modelMapper.map(usuarioDTOInput, Usuario.class);
    }

    public Usuario toModelDomainObjectSenha(UsuarioInput usuarioInput){
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public List<UsuarioDTO> toCollectionDomainObjects(List<Usuario> usuarios){
        return usuarios.stream()
                .map(usuario -> toModelDomainObject(usuario))
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> toCollectionDomainObjectSet(Set<Usuario> usuarios){
        return usuarios.stream()
                .map(usuario -> toModelDomainObject(usuario))
                .collect(Collectors.toList());
    }
}
