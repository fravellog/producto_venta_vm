package com.producto_venta_vm.cl.producto_venta_vm.controller;

import com.producto_venta_vm.cl.producto_venta_vm.model.Producto;
import com.producto_venta_vm.cl.producto_venta_vm.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.producto_venta_vm.cl.producto_venta_vm.assembler.ProductoModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/productos")
@Tag(name = "Producto Controller v1", description = "Operaciones CRUD básicas para productos con HATEOAS")
public class ProductoControllerV2 {

    private final ProductoService productoService;
    private final ProductoModelAssembler productoModelAssembler;

    public ProductoControllerV2(ProductoService productoService, ProductoModelAssembler productoModelAssembler) {
        this.productoService = productoService;
        this.productoModelAssembler = productoModelAssembler;
    }

    @Operation(summary = "Obtener todos los productos con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente.")
    @GetMapping
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
                .map(productoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }

    @Operation(summary = "Obtener un producto por ID con enlaces HATEOAS")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public EntityModel<Producto> getProductoById(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        return productoModelAssembler.toModel(producto);
    }

    @Operation(summary = "Crear un nuevo producto con enlaces HATEOAS")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @PostMapping
    public EntityModel<Producto> createProducto(@RequestBody Producto producto) {
        Producto savedProducto = productoService.save(producto);
        return productoModelAssembler.toModel(savedProducto);
    }

    @Operation(summary = "Actualizar un producto existente con enlaces HATEOAS")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto actualizado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public EntityModel<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto existingProducto = productoService.findById(id);
        if (existingProducto != null) {
            producto.setId(id);
            Producto updatedProducto = productoService.save(producto);
            return productoModelAssembler.toModel(updatedProducto);
        } else {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
    }

    @Operation(summary = "Eliminar un producto por ID con enlaces HATEOAS")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto eliminado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        if (producto != null) {
            productoService.deleteById(id);
        } else {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
    }

}
