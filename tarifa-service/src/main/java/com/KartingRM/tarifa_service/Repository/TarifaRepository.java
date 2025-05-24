package com.KartingRM.tarifa_service.Repository;

import com.KartingRM.tarifa_service.Entity.TarifaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<TarifaEntity, Long> {

    TarifaEntity findByTipo(int tipo);
}
