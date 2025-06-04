package com.KartingRM.rack_service.Service;

import com.KartingRM.rack_service.DTO.ReservaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RackService {

    @Autowired
    private RestTemplate restTemplate;

    public List<ReservaDTO> obtenerReservas() {
        String url = "http://gateway-server-service:8079/reserva/reservas";
        ResponseEntity<List<ReservaDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ReservaDTO>>() {
                }
        );
        return response.getBody();
    }
}
