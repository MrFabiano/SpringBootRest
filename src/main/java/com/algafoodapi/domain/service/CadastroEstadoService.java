package com.algafoodapi.domain.service;

import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.model.Estado;
import com.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe estado cadastrado";

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    }

    @Transactional
    public void excluir(Long estadoId){
        try {
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        }catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontraException(
                    String.format(MSG_ESTADO_NAO_ENCONTRADO + " %d", estadoId));
        }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Estado de codigo %d não pode ser removida, pois está em uso",
                    estadoId));
        }
    }



    public Estado buscarOuFalhar(Long estadoId){
        return estadoRepository.findById(estadoId)
                .orElseThrow(() ->
                        new EntidadeNaoEncontraException(String.format(MSG_ESTADO_NAO_ENCONTRADO + " %d", estadoId)));

    }
}

