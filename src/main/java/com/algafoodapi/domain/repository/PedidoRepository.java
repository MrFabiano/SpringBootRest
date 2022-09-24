package com.algafoodapi.domain.repository;

import com.algafoodapi.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>,
        JpaSpecificationExecutor<Pedido> {

    @Query("from Pedido where uuid = :uuid")
    Optional<Pedido> findByUUID(String uuid);

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante c join fetch c.cozinha")
    List<Pedido> findAll();
}
