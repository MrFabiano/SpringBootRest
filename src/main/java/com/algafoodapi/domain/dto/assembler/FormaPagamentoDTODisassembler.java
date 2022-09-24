package com.algafoodapi.domain.dto.assembler;

import com.algafoodapi.domain.input.FormaPagamentoInput;
import com.algafoodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toModel(FormaPagamentoInput formaPagamentoInput){
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void tocopyToModelObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento){
         modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}
