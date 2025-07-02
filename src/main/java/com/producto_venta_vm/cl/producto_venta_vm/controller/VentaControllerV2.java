package com.producto_venta_vm.cl.producto_venta_vm.controller;

import com.producto_venta_vm.cl.producto_venta_vm.model.Venta;
import com.producto_venta_vm.cl.producto_venta_vm.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.producto_venta_vm.cl.producto_venta_vm.assembler.VentaModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/ventas")
@Tag(name = "Venta Controller v2", description = "Operaciones CRUD para ventas con HATEOAS")
public class VentaControllerV2 {

    private final VentaService ventaService;
    private final VentaModelAssembler ventaModelAssembler;

    public VentaControllerV2(VentaService ventaService, VentaModelAssembler ventaModelAssembler) {
        this.ventaService = ventaService;
        this.ventaModelAssembler = ventaModelAssembler;
    }

    @Operation(summary = "Obtener todas las ventas con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente.")
    @GetMapping
    public CollectionModel<EntityModel<Venta>> getAllVentas() {
        List<EntityModel<Venta>> ventas = ventaService.obtenerTodo().stream()
                .map(ventaModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withSelfRel());
    }

    @Operation(summary = "Obtener una venta por ID con enlaces HATEOAS")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public EntityModel<Venta> getVentaById(@PathVariable Integer id) {
        Venta venta = ventaService.obtenerPorId(id);
        return ventaModelAssembler.toModel(venta);
    }

    @Operation(summary = "Crear una nueva venta con enlaces HATEOAS")
    @ApiResponse(responseCode = "201", description = "Venta creada exitosamente")
    @PostMapping
    public EntityModel<Venta> createVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.guardar(venta);
        return ventaModelAssembler.toModel(nuevaVenta);
    }

    @Operation(summary = "Actualizar una venta existente con enlaces HATEOAS")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping("/{id}")
    public EntityModel<Venta> updateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        Venta ventaActualizada = ventaService.obtenerPorId(id);
        if (ventaActualizada == null) {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
        venta.setId(id); // Aseguramos que el ID se mantenga
        Venta updatedVenta = ventaService.guardar(venta);
        return ventaModelAssembler.toModel(updatedVenta);
    }

    @Operation(summary = "Eliminar una venta por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Venta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public void deleteVenta(@PathVariable Integer id) {
        Venta venta = ventaService.obtenerPorId(id);
        if (venta == null) {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
        ventaService.eliminar(id);
    }
}
