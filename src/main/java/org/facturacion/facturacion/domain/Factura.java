package org.facturacion.facturacion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false, precision = 4)
    private Double subTotal;

    @Column(nullable = false, precision = 4)
    private Double total;

    @OneToMany(mappedBy = "factura")
    private List<DetalleFactura> detalleFacturaList;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Cliente cliente;
}
