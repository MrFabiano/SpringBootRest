package com.algafoodapi.config.modelmapper;

import com.algafoodapi.domain.dto.EnderecoDTO;
import com.algafoodapi.domain.input.ItemPedidoInput;
import com.algafoodapi.domain.model.Endereco;
import com.algafoodapi.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoToEnderecoModelTypeMap = modelMapper
                .createTypeMap(Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade()
                .getEstado().getNome(),(
                        //enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
                     enderecoModelDest, value) -> enderecoModelDest.getCidade());

        return  modelMapper;
    }
}
