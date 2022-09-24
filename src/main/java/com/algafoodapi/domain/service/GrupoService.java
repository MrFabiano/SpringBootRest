package com.algafoodapi.domain.service;

import com.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algafoodapi.domain.exception.EntidadeNaoEncontraException;
import com.algafoodapi.domain.model.Grupo;
import com.algafoodapi.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoService {

    private static final String GRUPO_NAO_ENCONTRADO = "O grupo não pode ser excluido, pois está em uso";
    private static final String GRUPO_EM_USO = "O grupo está em uso, tente novamente";

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);

    }

    @Transactional
    public void excluir(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeNaoEncontraException(String.format(GRUPO_NAO_ENCONTRADO, grupoId));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeEmUsoException(String.format(GRUPO_EM_USO, grupoId));
        }
    }

    public Grupo buscarOuFalhar(Long grupoId){
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new EntidadeEmUsoException(String.format("Grupo não incluso", grupoId)));
    }
}