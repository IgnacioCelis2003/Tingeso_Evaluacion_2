package com.KartingRM.tarifa_service.Service;

import com.KartingRM.tarifa_service.Entity.TarifaEntity;
import com.KartingRM.tarifa_service.Repository.TarifaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @PostConstruct
    public void initData() {
        if (tarifaRepository.count() == 0) {
            tarifaRepository.save(new TarifaEntity(1, 15000, 10)); // Tarifa para 1 persona
            tarifaRepository.save(new TarifaEntity(2, 20000, 15)); // Tarifa para 2 personas
            tarifaRepository.save(new TarifaEntity(3, 25000, 20)); // Tarifa para 3 personas
            System.out.println("Datos cargados.");
        }
    }

    public float getTarifaByTipo(int tipo) {
        if(tipo < 1 || tipo > 3) {
            throw new IllegalArgumentException("Tipo de tarifa inválido. Debe ser entre 1 y 3.");
        }
        return tarifaRepository.findByTipo(tipo).getPrecio();
    }
}
