package com.KartingRM.reserva_service.Repository;


import com.KartingRM.reserva_service.Entity.ComprobanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComprobanteRepository extends JpaRepository<ComprobanteEntity, Long> {


    List<ComprobanteEntity> findAll();

}
