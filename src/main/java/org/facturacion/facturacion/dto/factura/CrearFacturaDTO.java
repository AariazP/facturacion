package org.facturacion.facturacion.dto.factura;

import java.util.List;

public record CrearFacturaDTO(
        List<DetalleFacturaDTO> listDetalleFactura,
        Integer idUsuario,
        Integer idCliente
) {
}