package com.algafoodapi.domain.service;

import com.algafoodapi.domain.exception.ProdutoExceptionNaoEncontrado;
import com.algafoodapi.domain.model.Produto;
import com.algafoodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoExceptionNaoEncontrado(restauranteId, produtoId));
    }
}

