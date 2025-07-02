package com.producto_venta_vm.cl.producto_venta_vm.controller;

import com.producto_venta_vm.cl.producto_venta_vm.model.Venta;
import com.producto_venta_vm.cl.producto_venta_vm.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
@RestController
@Tag(name = "Venta Controller v1", description = "Operaciones CRUD para ventas")
@RequestMapping("/api/v1/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Obtener todas las ventas")
    @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente.")
    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaService.obtenerTodo();
        if (ventas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(ventas);
    }

    @Operation(summary = "Obtener una venta por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Integer id) {
        try {
            Venta venta = ventaService.obtenerPorId(id);
            return ResponseEntity.ok(venta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Crear una nueva venta")
    @ApiResponse(responseCode = "201", description = "Venta creada exitosamente")
    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.guardar(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la venta: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar una venta existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta actualizada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        try {
            Venta ventaExistente = ventaService.obtenerPorId(id);
            if (ventaExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada");
            }
            venta.setId(id);
            Venta ventaActualizada = ventaService.guardar(venta);
            ventaActualizada.setId(id);
            ventaActualizada.setIdUsuario(ventaExistente.getIdUsuario());
            ventaActualizada.setFecha(ventaExistente.getFecha());
            ventaActualizada.setTotal(ventaExistente.getTotal());
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la venta: " + e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una venta por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta eliminada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Integer id) {
        try {
            ventaService.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada");
        }
    }
}
