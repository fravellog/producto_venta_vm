package com.producto_venta_vm.cl.producto_venta_vm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service // Marca esta clase como un componente de servicio en Spring para inyección de dependencias.
public class GestionUsuariosClient {

    private final WebClient webClient; // Cliente HTTP reactivo para llamar al microservicio GestionUsuarios.

    // Constructor que inyecta el WebClient configurado con la URL base de GestionUsuarios.
    public GestionUsuariosClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // Método para obtener un usuario por su ID desde el microservicio GestionUsuarios.
    public Mono<String> getUsuarioPorId(Long id) {
        return webClient.get() // Realiza un GET HTTP
                .uri("/{id}", id) // A la ruta /{id}, reemplazando {id} con el valor recibido
                .retrieve() // Ejecuta la petición y espera la respuesta
                .bodyToMono(String.class); // Convierte la respuesta en un Mono<String> (programación reactiva)
    }

    // Método para obtener la lista completa de usuarios desde GestionUsuarios.
    public Mono<String> getAllUsuarios() {
        return webClient.get() // Realiza un GET HTTP
                .retrieve() // Ejecuta la petición y espera la respuesta
                .bodyToMono(String.class); // Convierte la respuesta en un Mono<String>
    }
}
