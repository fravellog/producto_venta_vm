package com.producto_venta_vm.cl.producto_venta_vm.controller;

import com.producto_venta_vm.cl.producto_venta_vm.model.Venta;
import com.producto_venta_vm.cl.producto_venta_vm.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

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
}
