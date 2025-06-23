package com.producto_venta_vm.cl.producto_venta_vm.repository;

import com.producto_venta_vm.cl.producto_venta_vm.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
}
