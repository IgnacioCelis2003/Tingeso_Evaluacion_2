package com.KartingRM.grupo_descuento_service.Repository;

import com.KartingRM.grupo_descuento_service.Entity.DescuentoGrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoGrupoRepository extends JpaRepository<DescuentoGrupoEntity, Long> {

}
