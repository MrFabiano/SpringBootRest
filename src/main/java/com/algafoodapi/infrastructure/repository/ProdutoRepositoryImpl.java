package com.algafoodapi.infrastructure.repository;

import com.algafoodapi.domain.model.FotoProduto;
import com.algafoodapi.domain.repository.ProdutoRepositoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

    @Autowired
    private EntityManager   entityManager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return entityManager.merge(fotoProduto);
    }

    @Transactional
    @Override
    public void delete(FotoProduto fotoProduto) {
            entityManager.remove(fotoProduto);
    }
}
