package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.controller.CidadeController;
import com.algafoodapi.controller.EstadoController;
import com.algafoodapi.domain.dto.CidadeDTO;
import com.algafoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTOAssembler() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Override
    public CidadeDTO toModel(Cidade cidade){
        CidadeDTO cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);

        cidadeDTO.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CidadeController.class)
                        .buscarPorId(cidadeDTO.getId())).withSelfRel());

        cidadeDTO.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel("cidades"));

        cidadeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                EstadoController.class).buscarPorId(cidadeDTO.getEstado().getId())).withSelfRel());


        return cidadeDTO;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }

    //    public CollectionModel<CidadeDTO> toCollectionDTO(List<Cidade> cidades){
//        return cidades.stream()
//                .map(cidade -> toModel(cidade))
//                .collect(Collectors.toList());
    //}
}
