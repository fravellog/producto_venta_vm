package com.producto_venta_vm.cl.producto_venta_vm.controller;

import com.producto_venta_vm.cl.producto_venta_vm.service.GestionUsuariosClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/external")
@Tag(name = "External API", description = "Endpoints externos para consumir GestionUsuarios")
public class ExternalController {

    private final GestionUsuariosClient gestionUsuariosClient;

    public ExternalController(GestionUsuariosClient gestionUsuariosClient) {
        this.gestionUsuariosClient = gestionUsuariosClient;
    }

    @GetMapping("/usuario/{id}")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Obtiene un usuario desde el microservicio GestionUsuarios dado su ID."
    )
    public Mono<String> getUsuario(@PathVariable Long id) {
        return gestionUsuariosClient.getUsuarioPorId(id);
    }

    @GetMapping("/usuarios")
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Obtiene la lista completa de usuarios desde el microservicio GestionUsuarios."
    )
    public Mono<String> getAllUsuarios() {
        return gestionUsuariosClient.getAllUsuarios();
    }
}
