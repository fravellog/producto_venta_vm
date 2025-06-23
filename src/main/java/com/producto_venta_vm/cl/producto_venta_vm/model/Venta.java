package com.producto_venta_vm.cl.producto_venta_vm.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venta")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private Integer idUsuario;

    @Column(nullable = false, length = 50)
    private Double total;

    @Column(nullable = false)
    private LocalDate fecha;
}
