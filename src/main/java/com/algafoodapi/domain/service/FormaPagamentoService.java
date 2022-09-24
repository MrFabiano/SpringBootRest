package com.algafoodapi.domain.service;

import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.exception.NegocioException;
import com.algafoodapi.domain.model.FormaPagamento;
import com.algafoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaPagamentoService {

    private static final String FORMA_PAGAMENTO_EM_USO = "Forma pagagamento já está em uso";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long formaPagamentoId){
        try{
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
        }catch(EmptyResultDataAccessException e){
            throw new NegocioException(e.getMessage());
        }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
        }

    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId){
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new EntidadeNaoEncontraException
                        (String.format("Forma pagamento não inclusa", formaPagamentoId)));
    }
}
