package org.facturacion.facturacion.services.implementation;

import org.facturacion.facturacion.config.KeepAliveScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * KeepAliveService implementation
 */
@Service
public class KeepAliveService {

    private final RestTemplate restTemplate;
    private final String selfUrl;

    /**
     * Este metodo realiza una petición GET al propio backend
     * para mantenerlo activo en Render.
     * @see KeepAliveScheduler
     * @param restTemplate Conifguracion de RestTemplate para realizar peticiones HTTP
     * @param selfUrl URL del backend
     */
    public KeepAliveService(RestTemplate restTemplate, @Value("${app.self-url}") String selfUrl) {
        this.restTemplate = restTemplate;
        this.selfUrl = selfUrl;
    }

    /**
     * Este metodo realiza una petición GET al propio backend
     * para mantenerlo activo en Render.
     */
    public void sendKeepAliveRequest() {
        try {
            // Realizamos la petición GET al propio backend
            ResponseEntity<String> response = restTemplate.getForEntity(selfUrl, String.class);
            //TODO: Implementar un log para guardar la respuesta del servidor
            System.out.println("Respuesta del servidor: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error al enviar la petición: " + e.getMessage());
        }
    }
}
