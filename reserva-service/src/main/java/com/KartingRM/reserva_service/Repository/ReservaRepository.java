package com.KartingRM.reserva_service.Repository;

import com.KartingRM.reserva_service.Entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    ReservaEntity findById(long id);

    List<ReservaEntity> findAll();

    List<ReservaEntity> findByRut(String rut);
}
