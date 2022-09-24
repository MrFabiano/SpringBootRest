package com.algafoodapi.domain.repository;

import com.algafoodapi.domain.model.FotoProduto;

public interface ProdutoRepositoryQuery {

    FotoProduto save(FotoProduto fotoProduto);
    void delete(FotoProduto fotoProduto);
}
