package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.input.RestauranteInput;
import com.algafoodapi.domain.model.Cidade;
import com.algafoodapi.domain.model.Cozinha;
import com.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;


    public Restaurante toDomainObject(RestauranteInput restauranteInput){
           return modelMapper.map(restauranteInput, Restaurante.class);

    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante  restaurante){
       // identifier of an instance of com.algafoodapi.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());
        if(restaurante.getEndereco() != null){
            restaurante.getEndereco().setCidade(new Cidade());
        }
        modelMapper.map(restauranteInput, restaurante);
    }
}
