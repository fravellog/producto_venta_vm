package com.producto_venta_vm.cl.producto_venta_vm.service;

import com.producto_venta_vm.cl.producto_venta_vm.model.Venta;
import com.producto_venta_vm.cl.producto_venta_vm.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> obtenerTodo() {
        return ventaRepository.findAll();
    }

    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }

    public void eliminar(Integer id) {
        ventaRepository.deleteById(id);
    }
}
