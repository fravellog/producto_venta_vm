package com.producto_venta_vm.cl.producto_venta_vm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration // Indica que esta clase es una clase de configuración de Spring y define beans.
public class WebClientConfig {

    @Value("${microservicio.gestion_usuarios.url}") // Inyecta el valor de la propiedad definida en application.properties.
    private String gestionUsuariosUrl;

    // Declara un bean de tipo WebClient que estará disponible en el contexto de Spring.
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(gestionUsuariosUrl) // Configura la URL base con la propiedad inyectada (URL de GestionUsuarios).
                .build(); // Construye la instancia de WebClient.
    }
}
