package com.algafoodapi.domain.service;

import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.model.Cozinha;
import com.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha não encontrada";
    public static final String MSG_COZINHA_EM_USO = "Cozinha não pode ser removida";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long cozinhaId){
        try {
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();
        }catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontraException(
                    String.format(MSG_COZINHA_NAO_ENCONTRADA + "cadastro de cozinha com codigo %d", cozinhaId));
    }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO + " %d não pode ser removida, pois está em uso",
                    cozinhaId));
        }
    }

    public Cozinha buscarOuFalhar(Long cozinhaId){
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontraException(String.format(MSG_COZINHA_NAO_ENCONTRADA + " %d não pode ser removida, pois não foi encontrada",
                        cozinhaId)));

    }

}
