package com.algafoodapi.domain.repository;

import com.algafoodapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {

    @Query("from Restaurante r join fetch r.cozinha join fetch r.formaPagamentos")
    List<Restaurante> findAll();

    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial,
                                  BigDecimal taxaFreteFinal);

    }

