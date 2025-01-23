package org.facturacion.facturacion.dto.producto;

import org.facturacion.facturacion.domain.Producto;
import org.facturacion.facturacion.dto.formaVenta.FormaVentaDTO;

import java.util.List;

public record CrearProductoDTO(
        String codigo,
        String nombre,
        String activo,
        String impuesto,
        List<FormaVentaDTO> formasVenta
) {

    public Producto toEntity() {
        Producto producto = new Producto();
        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setActivo(activo.equals("1"));
        producto.setEliminado(false);
        producto.setFechaCreacion(new java.util.Date());
        producto.setFormaVentas(formasVenta.stream().map(fv -> FormaVentaDTO.toEntity(fv, producto)).toList());
        return producto;
    }
}
