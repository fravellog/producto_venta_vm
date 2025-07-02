package com.producto_venta_vm.cl.producto_venta_vm.assembler;

import com.producto_venta_vm.cl.producto_venta_vm.model.Venta;
import com.producto_venta_vm.cl.producto_venta_vm.controller.VentaControllerV2;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {
    @Override
    @NonNull
    public EntityModel<Venta> toModel(@NonNull Venta venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentaControllerV2.class).getVentaById(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withRel("ventas"));
    }
}
