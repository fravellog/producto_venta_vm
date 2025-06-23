package com.producto_venta_vm.cl.producto_venta_vm.repository;

import com.producto_venta_vm.cl.producto_venta_vm.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario

}
