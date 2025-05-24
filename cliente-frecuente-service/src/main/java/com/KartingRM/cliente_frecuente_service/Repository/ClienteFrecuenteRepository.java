package com.KartingRM.cliente_frecuente_service.Repository;

import com.KartingRM.cliente_frecuente_service.Entity.ClienteFrecuenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteFrecuenteRepository extends JpaRepository<ClienteFrecuenteEntity, Long> {
    List<ClienteFrecuenteEntity> findByRut(String rut);
}
